package com.bencha.services.tmdb;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TmdbMovieService {
    private final TmdbMovies tmdbMovies;

    @Inject
    public TmdbMovieService(
        TmdbApi tmdbApi
    ) {
        this.tmdbMovies = tmdbApi.getMovies();
    }

    public MovieDb getMovieById(Long id) {
        return this.tmdbMovies.getMovie(id.intValue(), "fr");
    }
}
