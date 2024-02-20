package com.bencha.services.tmdb;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
@Slf4j
public class TmdbMovieService {
    private final TmdbMovies tmdbMovies;
    private final TmdbCacheService tmdbCacheService;

    @Inject
    public TmdbMovieService(
        TmdbApi tmdbApi,
        TmdbCacheService tmdbCacheService
    ) {
        this.tmdbMovies = tmdbApi.getMovies();
        this.tmdbCacheService = tmdbCacheService;
    }

    private MovieDb getMovieByIdFromTmdb(Long id) {
        return this.tmdbMovies.getMovie(id.intValue(), "fr");
    }

    public MovieDb getMovieById(Long id) {
        return this.tmdbCacheService
            .getMovieInCache(String.valueOf(id))
            .orElseGet(() -> {
                logger.debug("Movie {} not found in cache retrieving it", id);
                return this.getMovieByIdFromTmdb(id);
            });
    }

    public MovieDb getMovieWithCreditsById(Long id) {
        return this.tmdbMovies.getMovie(id.intValue(), "fr", TmdbMovies.MovieMethod.credits);
    }

    public void loadMoviesInCache(Set<Long> movieIds) {
        movieIds
            .forEach(id -> {
                MovieDb movieDb = this.getMovieByIdFromTmdb(id);
                tmdbCacheService.putMovieInCache(String.valueOf(movieDb.getId()), movieDb);
            });
    }
}
