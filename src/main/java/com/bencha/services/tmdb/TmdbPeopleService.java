package com.bencha.services.tmdb;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbPeople;
import info.movito.themoviedbapi.model.Artwork;
import info.movito.themoviedbapi.model.people.PersonPeople;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Singleton
@Slf4j
public class TmdbPeopleService {
    private final TmdbPeople tmdbPeople;
    private final TmdbCacheService tmdbCacheService;

    @Inject
    public TmdbPeopleService(
        TmdbApi tmdbApi,
        TmdbCacheService tmdbCacheService
    ) {
        this.tmdbPeople = tmdbApi.getPeople();
        this.tmdbCacheService = tmdbCacheService;
    }

    private PersonPeople getPeopleById(Long id) {
        return this.tmdbPeople.getPersonInfo(id.intValue());
    }

    private Optional<Artwork> getPeopleImage(Long id) {
        return this.tmdbPeople.getPersonImages(id.intValue())
            .stream().findFirst();
    }

    public PersonWithArtwork getPersonWithArtworkById(Long id, boolean shouldFetchArtwork) {
        return tmdbCacheService
            .getPersonInCache(String.valueOf(id))
            .orElseGet(() -> {
                logger.debug("Person {} not found in cache retrieving it", id);
                PersonPeople people = this.getPeopleById(id);
                Optional<Artwork> artwork = shouldFetchArtwork ? this.getPeopleImage(id) : Optional.empty();
                return PersonWithArtwork
                    .of(
                        id,
                        artwork,
                        people
                    );
            });
    }

    public void loadPersonsInCache(Set<Long> personIds, Map<Long, Boolean> shouldFetchArtwork) {
        personIds
            .forEach(id -> {
                PersonPeople people = this.getPeopleById(id);
                Optional<Artwork> artwork = shouldFetchArtwork.get(id) ? this.getPeopleImage(id) : Optional.empty();
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
