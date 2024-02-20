package com.bencha.services;

import com.bencha.db.dao.AwardDao;
import com.bencha.db.dao.AwardNomineeDao;
import com.bencha.db.generated.AwardNominee;
import com.bencha.db.generated.Pronostic;
import com.bencha.enums.AwardsType;
import com.bencha.services.tmdb.PersonWithArtwork;
import com.bencha.services.tmdb.TmdbConfigurationService;
import com.bencha.services.tmdb.TmdbMovieService;
import com.bencha.services.tmdb.TmdbPeopleService;
import com.bencha.webservices.beans.Nominee;
import com.bencha.webservices.beans.NomineeRequest;
import com.bencha.webservices.beans.PronosticChoice;
import info.movito.themoviedbapi.model.MovieDb;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class AwardNomineeService {
    private final AwardNomineeDao awardNomineeDao;
    private final AwardDao awardDao;
    private final TmdbMovieService tmdbMovieService;
    private final TmdbPeopleService tmdbPeopleService;
    private final TmdbConfigurationService tmdbConfigurationService;

    @Inject
    public AwardNomineeService(
        AwardNomineeDao awardNomineeDao,
        AwardDao awardDao,
        TmdbMovieService tmdbMovieService,
        TmdbConfigurationService tmdbConfigurationService,
        TmdbPeopleService tmdbPeopleService
    ) {
        this.awardNomineeDao = awardNomineeDao;
        this.awardDao = awardDao;
        this.tmdbMovieService = tmdbMovieService;
        this.tmdbPeopleService = tmdbPeopleService;
        this.tmdbConfigurationService = tmdbConfigurationService;
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
            .map(tmdbMovieService::getMovieById)
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
            .map(id -> tmdbPeopleService.getPersonWithArtworkById(id, shouldFetchArtwork.get(id)))
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
    public PronosticChoice pronosticToPronosticChoice(Pronostic pronostic) {
        PronosticChoice pronosticChoice = new PronosticChoice();
        pronosticChoice.setNomineeId(pronostic.getNomineeId());
        pronosticChoice.setAwardId(pronostic.getAwardId());
        if (pronostic.getNomineeId() == null) {
            AwardsType awardsType = AwardsType.valueOf(awardDao.findById(pronostic.getAwardId()).getType());
            Nominee nomineeDto = new Nominee();
            nomineeDto = convertSearchToNominee(NomineeRequest.of(
                pronostic.getTdmbMovieId(),
                pronostic.getTdmbPersonId(),
                awardsType
            ));
            pronosticChoice.setNominee(nomineeDto);
        }
        return pronosticChoice;
    }

    public Nominee pronosticToNominee(Pronostic pronostic, AwardNominee nominee, AwardsType awardsType) {
        Nominee nomineeDto = new Nominee();
        if (pronostic.getNomineeId() == null) {
            nomineeDto = convertSearchToNominee(
                NomineeRequest.of(
                    pronostic.getTdmbMovieId(),
                    pronostic.getTdmbPersonId(),
                    awardsType
                )
            );
        } else {
            MovieDb movieDb = tmdbMovieService.getMovieById(nominee.getTdmbMovieId());
            buildNomineeMovie(nominee, movieDb, nomineeDto);
            if (AwardsType.CREW.equals(awardsType) || AwardsType.CAST.equals(awardsType)) {
                if (nominee.getTdmbPersonId() != null) {
                    PersonWithArtwork personWithArtwork = tmdbPeopleService.getPersonWithArtworkById(nominee.getTdmbPersonId(), AwardsType.CAST.equals(awardsType));
                    nomineeDto.setPersonName(personWithArtwork.getPerson().getName());
                    nomineeDto.setTmdbPersonId(personWithArtwork.getPersonId().intValue());
                    if (personWithArtwork.getArtwork().isPresent()) {
                        nomineeDto.setPersonMediaUrl(tmdbConfigurationService.buildMediaUrl(personWithArtwork.getArtwork().get().getFilePath()));
                    }
                } else {
                    nomineeDto.setPersonName(nominee.getNameOverride());
                }
            }
        }
        return nomineeDto;
    }

    public List<Nominee> findAwardCrewNomineesDto(List<AwardNominee> awardNominees, Map<Long, MovieDb> movies, Map<Long, PersonWithArtwork> persons) {
        return awardNominees.stream()
            .map(nominee -> {
                MovieDb movieDb = movies.get(nominee.getTdmbMovieId());
                PersonWithArtwork personWithArtwork = persons.get(nominee.getTdmbPersonId());
                Nominee nomineeDto = new Nominee();
                if (nominee.getNameOverride() != null) {
                    nomineeDto.setPersonName(nominee.getNameOverride());
                } else {
                    nomineeDto.setPersonName(personWithArtwork.getPerson().getName());
                }
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
        this.tmdbMovieService.loadMoviesInCache(movieIds);
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
        this.tmdbPeopleService.loadPersonsInCache(personIds, shouldFetchArtwork);
    }

    public Nominee convertSearchToNominee(NomineeRequest nomineeRequest) {
        MovieDb movieDb = tmdbMovieService.getMovieById(nomineeRequest.getMovieId());
        Nominee nomineeDto = new Nominee();
        nomineeDto.setMovieTitle(movieDb.getOriginalTitle());
        nomineeDto.setFrenchMovieTitle(movieDb.getTitle());
        nomineeDto.setTmdbMovieId(movieDb.getId());
        nomineeDto.setMovieMediaUrl(tmdbConfigurationService.buildMediaUrl(movieDb.getPosterPath()));
        if (nomineeRequest.getAwardsType().equals(AwardsType.CAST) || nomineeRequest.getAwardsType().equals(AwardsType.CREW)) {
            PersonWithArtwork personWithArtwork = tmdbPeopleService.getPersonWithArtworkById(nomineeRequest.getPersonId(), nomineeRequest.getAwardsType().equals(AwardsType.CAST));
            nomineeDto.setPersonName(personWithArtwork.getPerson().getName());
            nomineeDto.setTmdbPersonId(personWithArtwork.getPersonId().intValue());
            if (personWithArtwork.getArtwork().isPresent()) {
                nomineeDto.setPersonMediaUrl(tmdbConfigurationService.buildMediaUrl(personWithArtwork.getArtwork().get().getFilePath()));
            }
        }
        return nomineeDto;
    }
}
