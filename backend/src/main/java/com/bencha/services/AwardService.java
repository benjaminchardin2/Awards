package com.bencha.services;

import com.bencha.db.dao.AwardDao;
import com.bencha.db.generated.Award;
import com.bencha.db.generated.AwardNominee;
import com.bencha.enums.AwardsType;
import com.bencha.services.tmdb.PersonWithArtwork;
import com.bencha.webservices.beans.AwardWithNominees;
import info.movito.themoviedbapi.model.MovieDb;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class AwardService {
    private final AwardDao awardDao;
    private final AwardNomineeService awardNomineeService;

    @Inject
    public AwardService(AwardDao awardDao, AwardNomineeService awardNomineeService) {
        this.awardDao = awardDao;
        this.awardNomineeService = awardNomineeService;
    }

    public List<AwardWithNominees> findCeremonyAwardsDto(Long ceremonyId) {
        List<Award> awards = this.awardDao.findAwardsByCeremonyId(ceremonyId);
        Map<Long, AwardsType> awardsTypeMap = awards.stream()
            .collect(Collectors.toMap(
                Award::getId,
                award -> AwardsType.valueOf(award.getType())
            ));
        List<AwardNominee> awardNominees = this.awardNomineeService.findAwardsNominees(awards.stream().map(Award::getId).toList());
        Map<Long, MovieDb> movies = this.awardNomineeService.findMovies(awardNominees);
        Map<Long, PersonWithArtwork> persons = this.awardNomineeService.findPersons(awardNominees, awardsTypeMap);
        Map<Long, List<AwardNominee>> awardNomineesByAwardId = awardNominees.stream().collect(Collectors.groupingBy(AwardNominee::getAwardId));
        return awards.stream()
            .map(award -> {
                AwardsType awardsType = AwardsType.valueOf(award.getType());
                AwardWithNominees awardWithNominees = new AwardWithNominees();
                awardWithNominees.setAwardId(award.getId());
                awardWithNominees.setAwardName(award.getName());
                awardWithNominees.setType(awardsType);
                switch (awardsType) {
                    case MOVIE -> {
                        awardWithNominees.setNominees(awardNomineeService.findAwardMoviesNomineesDto(awardNomineesByAwardId.get(award.getId()), movies));
                    }
                    case CAST -> {
                        awardWithNominees.setNominees(awardNomineeService.findAwardCastNomineesDto(awardNomineesByAwardId.get(award.getId()), movies, persons));
                    }
                    case CREW -> {
                        awardWithNominees.setNominees(awardNomineeService.findAwardCrewNomineesDto(awardNomineesByAwardId.get(award.getId()), movies, persons));
                    }
                }
                return awardWithNominees;
            })
            .toList();
    }

    public void loadCacheForCeremony(Long ceremonyId) {
        Long timeInMillis = Instant.now().toEpochMilli();
        logger.debug("Starting cache load for ceremony {}", ceremonyId);
        List<Award> awards = this.awardDao.findAwardsByCeremonyId(ceremonyId);
        Map<Long, AwardsType> awardsTypeMap = awards.stream()
            .collect(Collectors.toMap(
                Award::getId,
                award -> AwardsType.valueOf(award.getType())
            ));
        List<AwardNominee> awardNominees = this.awardNomineeService.findAwardsNominees(awards.stream().map(Award::getId).toList());
        this.awardNomineeService.loadMoviesInCache(awardNominees);
        this.awardNomineeService.loadPersonsInCache(awardNominees, awardsTypeMap);
        logger.debug("Ending cache load for ceremony {} in {}ms", ceremonyId, Instant.now().toEpochMilli() - timeInMillis);
    }
}
