package com.bencha.services;

import com.bencha.db.dao.AwardNomineeDao;
import com.bencha.db.generated.AwardNominee;
import com.bencha.enums.AwardsType;
import com.bencha.services.tmdb.*;
import com.bencha.webservices.beans.Nominee;
import info.movito.themoviedbapi.model.Artwork;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.PersonPeople;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class AwardNomineeService {
    private final AwardNomineeDao awardNomineeDao;
    private final TmdbMovieService tmdbMovieService;
    private final TmdbPeopleService tmdbPeopleService;
    private final TmdbCacheService tmdbCacheService;

    private final TmdbConfigurationService tmdbConfigurationService;

    @Inject
    public AwardNomineeService(
        AwardNomineeDao awardNomineeDao,
        TmdbMovieService tmdbMovieService,
        TmdbConfigurationService tmdbConfigurationService,
        TmdbPeopleService tmdbPeopleService,
        TmdbCacheService tmdbCacheService
    ) {
        this.awardNomineeDao = awardNomineeDao;
        this.tmdbMovieService = tmdbMovieService;
        this.tmdbPeopleService = tmdbPeopleService;
        this.tmdbConfigurationService = tmdbConfigurationService;
        this.tmdbCacheService = tmdbCacheService;
    }

    public List<AwardNominee> findAwardsNominees(List<Long> awardIds) {
        return this.awardNomineeDao.findAwardsAwardNominees(awardIds);
    }

    public Map<Long, MovieDb> findMovies(List<AwardNominee> awardNominees) {
        Set<Long> movieIds = awardNominees
            .stream()
            .map(AwardNominee::getTdmbMovieId)
            .collect(Collectors.toSet());
        return movieIds
            .parallelStream()
            .map(id -> tmdbCacheService
                .getMovieInCache(String.valueOf(id))
                .orElseGet(() -> {
                    logger.debug("Movie {} not found in cache retrieving it", id);
                    return tmdbMovieService.getMovieById(id);
                })
            )
            .collect(Collectors.toMap(
                movie -> Long.valueOf(movie.getId()),
                Function.identity()
            ));
    }

    public Map<Long, PersonWithArtwork> findPersons(List<AwardNominee> awardNominees, Map<Long, AwardsType> awardsTypeMap) {
        Set<Long> personIds = awardNominees
            .stream()
            .map(AwardNominee::getTdmbPersonId)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        Map<Long, Boolean> shouldFetchArtwork = awardNominees
            .stream()
            .collect(
                Collectors.toMap(
                    AwardNominee::getTdmbPersonId,
                    awardNominee -> awardsTypeMap.get(awardNominee.getAwardId()).equals(AwardsType.CAST),
                    (a, b) -> Boolean.TRUE.equals(a) ? a : b
                )
            );
        return personIds
            .parallelStream()
            .map(id -> tmdbCacheService
                .getPersonInCache(String.valueOf(id))
                .orElseGet(() -> {
                    logger.debug("Person {} not found in cache retrieving it", id);
                    PersonPeople people = tmdbPeopleService.getPeopleById(id);
                    Optional<Artwork> artwork = shouldFetchArtwork.get(id) ? tmdbPeopleService.getPeopleImage(id) : Optional.empty();
                    return PersonWithArtwork
                        .of(
                            id,
                            artwork,
                            people
                        );
                }))
            .collect(Collectors.toMap(
                PersonWithArtwork::getPersonId,
                Function.identity()
            ));
    }

    public List<Nominee> findAwardCastNomineesDto(List<AwardNominee> awardNominees, Map<Long, MovieDb> movies, Map<Long, PersonWithArtwork> persons) {
        return awardNominees.stream()
            .map(nominee -> {
                MovieDb movieDb = movies.get(nominee.getTdmbMovieId());
                PersonWithArtwork personWithArtwork = persons.get(nominee.getTdmbPersonId());
                Nominee nomineeDto = new Nominee();
                nomineeDto.setPersonName(personWithArtwork.getPerson().getName());
                nomineeDto.setTmdbPersonId(personWithArtwork.getPersonId().intValue());
                if (personWithArtwork.getArtwork().isPresent()) {
                    nomineeDto.setPersonMediaUrl(tmdbConfigurationService.buildMediaUrl(personWithArtwork.getArtwork().get().getFilePath()));
                }
                return buildNomineeMovie(nominee, movieDb, nomineeDto);
            })
            .toList();
    }

    public List<Nominee> findAwardCrewNomineesDto(List<AwardNominee> awardNominees, Map<Long, MovieDb> movies, Map<Long, PersonWithArtwork> persons) {
        return awardNominees.stream()
            .map(nominee -> {
                MovieDb movieDb = movies.get(nominee.getTdmbMovieId());
                PersonWithArtwork personWithArtwork = persons.get(nominee.getTdmbPersonId());
                Nominee nomineeDto = new Nominee();
                nomineeDto.setPersonName(personWithArtwork.getPerson().getName());
                return buildNomineeMovie(nominee, movieDb, nomineeDto);
            })
            .toList();
    }

    @NotNull
    private Nominee buildNomineeMovie(AwardNominee nominee, MovieDb movieDb, Nominee nomineeDto) {
        nomineeDto.setNomineeId(nominee.getId());
        nomineeDto.setMovieTitle(movieDb.getOriginalTitle());
        nomineeDto.setFrenchMovieTitle(movieDb.getTitle());
        nomineeDto.setTmdbMovieId(nominee.getTdmbMovieId().intValue());
        nomineeDto.setTmdbMovieId(nominee.getTdmbMovieId().intValue());
        nomineeDto.setMovieMediaUrl(tmdbConfigurationService.buildMediaUrl(movieDb.getPosterPath()));
        return nomineeDto;
    }

    public List<Nominee> findAwardMoviesNomineesDto(List<AwardNominee> awardNominees, Map<Long, MovieDb> movies) {
        return awardNominees.stream()
            .map(nominee -> {
                MovieDb movieDb = movies.get(nominee.getTdmbMovieId());
                Nominee nomineeDto = new Nominee();
                return buildNomineeMovie(nominee, movieDb, nomineeDto);
            })
            .toList();
    }

    public void loadMoviesInCache(List<AwardNominee> awardNominees) {
        Set<Long> movieIds = awardNominees
            .stream()
            .map(AwardNominee::getTdmbMovieId)
            .collect(Collectors.toSet());
        movieIds
            .forEach(id -> {
                MovieDb movieDb = tmdbMovieService.getMovieById(id);
                tmdbCacheService.putMovieInCache(String.valueOf(movieDb.getId()), movieDb);
            });
    }

    public void loadPersonsInCache(List<AwardNominee> awardNominees, Map<Long, AwardsType> awardsTypeMap) {
        Set<Long> personIds = awardNominees
            .stream()
            .map(AwardNominee::getTdmbPersonId)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        Map<Long, Boolean> shouldFetchArtwork = awardNominees
            .stream()
            .collect(
                Collectors.toMap(
                    AwardNominee::getTdmbPersonId,
                    awardNominee -> awardsTypeMap.get(awardNominee.getAwardId()).equals(AwardsType.CAST),
                    (a, b) -> Boolean.TRUE.equals(a) ? a : b
                )
            );
        personIds
            .forEach(id -> {
                PersonPeople people = tmdbPeopleService.getPeopleById(id);
                Optional<Artwork> artwork = shouldFetchArtwork.get(id) ? tmdbPeopleService.getPeopleImage(id) : Optional.empty();
                PersonWithArtwork personWithArtwork = PersonWithArtwork
                    .of(
                        id,
                        artwork,
                        people
                    );
                tmdbCacheService.putPersonInCache(String.valueOf(people.getId()), personWithArtwork);
            });
    }
}
