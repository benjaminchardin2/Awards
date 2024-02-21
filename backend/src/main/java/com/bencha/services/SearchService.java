package com.bencha.services;

import com.bencha.db.dao.AwardConditionDao;
import com.bencha.db.dao.AwardDao;
import com.bencha.db.dao.TranslationDao;
import com.bencha.db.generated.Award;
import com.bencha.db.generated.AwardCondition;
import com.bencha.enums.AwardConditionEnum;
import com.bencha.enums.AwardsType;
import com.bencha.services.tmdb.TmdbMovieService;
import com.bencha.services.tmdb.TmdbPeopleService;
import com.bencha.services.tmdb.TmdbSearchService;
import com.bencha.webservices.beans.AdditionalResults;
import com.bencha.webservices.beans.Nominee;
import com.bencha.webservices.beans.NomineeRequest;
import com.bencha.webservices.beans.SearchResult;
import com.google.common.base.Strings;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCredit;
import info.movito.themoviedbapi.model.people.PersonCrew;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Singleton
public class SearchService {
    private final TmdbSearchService tmdbSearchService;
    private final TmdbPeopleService tmdbPeopleService;
    private final TmdbMovieService tmdbMovieService;
    private final AwardNomineeService awardNomineeService;
    private final AwardConditionDao awardConditionDao;
    private final TranslationDao translationDao;
    private final AwardDao awardDao;
    private final static Integer pageSize = 5;

    @Inject
    public SearchService(
        TmdbSearchService tmdbSearchService,
        TmdbPeopleService tmdbPeopleService,
        TmdbMovieService tmdbMovieService,
        AwardNomineeService awardNomineeService,
        AwardConditionDao awardConditionDao,
        TranslationDao translationDao,
        AwardDao awardDao
    ) {
        this.tmdbSearchService = tmdbSearchService;
        this.tmdbPeopleService = tmdbPeopleService;
        this.tmdbMovieService = tmdbMovieService;
        this.awardNomineeService = awardNomineeService;
        this.awardConditionDao = awardConditionDao;
        this.translationDao = translationDao;
        this.awardDao = awardDao;
    }

    public List<SearchResult> searchMovies(String search, Integer page) {
        return this.tmdbSearchService
            .searchMovies(search, page)
            .stream()
            .map(movieDb -> SearchResult
                .of(
                    movieDb.getId(),
                    movieDb.getTitle()
                ))
            .toList();
    }

    public List<SearchResult> searchPersons(String search, Integer page) {
        return this.tmdbSearchService
            .searchPersons(search, page)
            .stream()
            .map(person -> SearchResult
                .of(
                    person.getId(),
                    person.getName()
                ))
            .toList();
    }

    public AdditionalResults searchPersonCredits(Long awardId, Long personId, int page) {
        Award award = awardDao.findById(awardId);
        List<AwardCondition> awardConditions = this.awardConditionDao.findByAwardIdAndConditionNames(awardId, AwardConditionEnum.personCreditMatchersName());
        Map<AwardCondition,AwardConditionEnum> awardConditionEnumMap = awardConditions
            .stream()
            .collect(Collectors.toMap(
                Function.identity(),
                awdc -> AwardConditionEnum.findByConditionName(awdc.getConditionName()).orElse(null)
            ));
        List<PersonCredit> personCredits = this.tmdbPeopleService.getPersonCredits(personId, AwardsType.valueOf(award.getType()));
        List<SearchResult> searchResults = personCredits
            .stream()
            .skip((long) pageSize * page)
            .sorted((a, b) -> {
                int compare = Math.toIntExact(awardConditionEnumMap.entrySet().stream()
                    .filter(entry -> entry.getValue().getPersonCreditMatcher().apply(b, entry.getKey().getValue()))
                    .count() - awardConditionEnumMap.entrySet().stream()
                    .filter(entry -> entry.getValue().getPersonCreditMatcher().apply(a, entry.getKey().getValue()))
                    .count());
                if (compare != 0) {
                    return compare;
                }
                if (b.getPopularity() - a.getPopularity() != 0) {
                    return (int) (b.getPopularity() - a.getPopularity());
                }
                if (!Strings.isNullOrEmpty(a.getReleaseDate()) && !Strings.isNullOrEmpty(b.getReleaseDate())) {
                    return LocalDate.parse(b.getReleaseDate(), DateTimeFormatter.ISO_DATE).compareTo(LocalDate.parse(a.getReleaseDate(), DateTimeFormatter.ISO_DATE));
                }
                return 1;
            })
            .limit(pageSize)
            .map(pc -> SearchResult.of(pc.getMediaId(), pc.getMovieOriginalTitle()))
            .toList();
        Boolean hasMore = (((page + 1) * pageSize) <= personCredits.size());
        return AdditionalResults.of(
            searchResults,
            hasMore,
            page + 1
        );
    }

    public AdditionalResults searchMoviePersons(Long awardId, Long movieId, int page) {
        Award award = awardDao.findById(awardId);
        AwardsType awardsType = AwardsType.valueOf(award.getType());
        MovieDb movieDb = this.tmdbMovieService.getMovieWithCreditsById(movieId);
        List<SearchResult> searchResults;
        Boolean hasMore = false;
        if (awardsType.equals(AwardsType.CREW)) {
            List<AwardCondition> awardConditions = this.awardConditionDao.findByAwardIdAndConditionNames(awardId, AwardConditionEnum.personCrewMatchersName());
            Map<AwardCondition,AwardConditionEnum> awardConditionEnumMap = awardConditions
                .stream()
                .collect(Collectors.toMap(
                    Function.identity(),
                    awdc -> AwardConditionEnum.findByConditionName(awdc.getConditionName()).orElse(null)
                ));
            List<PersonCrew> personCrews = movieDb.getCrew();
            searchResults = personCrews
                .stream()
                .skip((long) pageSize * page)
                .sorted((a, b) -> Math.toIntExact(awardConditionEnumMap.entrySet().stream()
                    .filter(entry -> entry.getValue().getPersonCrewMatcher().apply(b, entry.getKey().getValue()))
                    .count() - awardConditionEnumMap.entrySet().stream()
                    .filter(entry -> entry.getValue().getPersonCrewMatcher().apply(a, entry.getKey().getValue()))
                    .count()))
                .limit(pageSize)
                .map(pc -> SearchResult.of(pc.getId(), pc.getName() + " - " + translationDao.findTranslation(pc.getJob()).orElse(pc.getJob())))
                .toList();
            if (((page + 1) * pageSize) <= personCrews.size()) {
                hasMore = true;
            }
        } else if (awardsType.equals(AwardsType.CAST)) {
            List<AwardCondition> awardConditions = this.awardConditionDao.findByAwardIdAndConditionNames(awardId, AwardConditionEnum.personCastMatchersName());
            Map<AwardCondition,AwardConditionEnum> awardConditionEnumMap = awardConditions
                .stream()
                .collect(Collectors.toMap(
                    Function.identity(),
                    awdc -> AwardConditionEnum.findByConditionName(awdc.getConditionName()).orElse(null)
                ));
            List<PersonCast> personCasts = movieDb.getCast();
            searchResults = personCasts
                .stream()
                .skip((long) pageSize * page)
                .sorted((a, b) -> Math.toIntExact(awardConditionEnumMap.entrySet().stream()
                    .filter(entry -> entry.getValue().getPersonCastMatcher().apply(b, entry.getKey().getValue()))
                    .count() - awardConditionEnumMap.entrySet().stream()
                    .filter(entry -> entry.getValue().getPersonCastMatcher().apply(a, entry.getKey().getValue()))
                    .count()))
                .limit(pageSize)
                .map(pc -> SearchResult.of(pc.getId(), pc.getName() + " - " + pc.getCharacter()))
                .toList();
            if (((page + 1) * pageSize) <= personCasts.size()) {
                hasMore = true;
            }
        } else {
            searchResults = List.of();
        }
        return AdditionalResults.of(
            searchResults,
            hasMore,
            page + 1
        );
    }

    public Nominee convertSearchToNominee(NomineeRequest nomineeRequest) {
        return awardNomineeService.convertSearchToNominee(nomineeRequest);
    }
}
