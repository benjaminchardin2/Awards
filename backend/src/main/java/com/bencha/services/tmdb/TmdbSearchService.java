package com.bencha.services.tmdb;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.Person;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Slf4j
public class TmdbSearchService {
    private final TmdbSearch tmdbSearch;

    @Inject
    public TmdbSearchService(
        TmdbApi tmdbApi
    ) {
        this.tmdbSearch = tmdbApi.getSearch();
    }

    public List<MovieDb> searchMovies(String search, int page) {
        return this.tmdbSearch.searchMovie(
            search,
            null,
            "fr-FR",
            false,
            page
        ).getResults();
    }

    public List<Person> searchPersons(String search, int page) {
        return this.tmdbSearch.searchPerson(
            search,
            false,
            page
        ).getResults();
    }
}
