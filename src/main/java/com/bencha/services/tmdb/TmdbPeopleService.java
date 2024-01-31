package com.bencha.services.tmdb;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbPeople;
import info.movito.themoviedbapi.model.Artwork;
import info.movito.themoviedbapi.model.people.PersonPeople;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class TmdbPeopleService {
    private final TmdbPeople tmdbPeople;

    @Inject
    public TmdbPeopleService(
        TmdbApi tmdbApi
    ) {
        this.tmdbPeople = tmdbApi.getPeople();
    }

    public PersonPeople getPeopleById(Long id) {
        return this.tmdbPeople.getPersonInfo(id.intValue());
    }

    public Optional<Artwork> getPeopleImage(Long id) {
        return this.tmdbPeople.getPersonImages(id.intValue())
            .stream().findFirst();
    }
}
