package com.bencha.services.tmdb;

import com.bencha.enums.TmdbCacheEnum;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import info.movito.themoviedbapi.model.MovieDb;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class TmdbCacheService {
    private Map<String, Cache<String, Object>> caches = new HashMap<>();

    @Inject
    public TmdbCacheService() {
        Arrays.stream(TmdbCacheEnum
            .values())
            .forEach(cacheEnum -> {
                Cache<String, Object> cache = Caffeine.newBuilder()
                    .expireAfterWrite(cacheEnum.getDuration())
                    .maximumSize(cacheEnum.getSize())
                    .build();
                caches.put(cacheEnum.getCacheKey(), cache);
            });
    }

    private <T> void putInCache(String cacheName, String cacheKey, T object) {
        Cache<String, Object> cache = caches.get(cacheName);
        cache.put(cacheKey, object);
    }

    private <T> T getFromCache(String cacheName, String cacheKey, Class<T> type) {
        Cache<String, Object> cache = caches.get(cacheName);
        return type.cast(cache.getIfPresent(cacheKey));
    }

    public void putMovieInCache(String cacheKey, MovieDb movieDb) {
        putInCache(TmdbCacheEnum.MOVIE_CACHE.getCacheKey(), cacheKey, movieDb);
    }

    public void putPersonInCache(String cacheKey, PersonWithArtwork person) {
        putInCache(TmdbCacheEnum.PERSON_CACHE.getCacheKey(), cacheKey, person);
    }

    public Optional<MovieDb> getMovieInCache(String cacheKey) {
        return Optional.ofNullable(getFromCache(TmdbCacheEnum.MOVIE_CACHE.getCacheKey(), cacheKey, MovieDb.class));
    }

    public Optional<PersonWithArtwork> getPersonInCache(String cacheKey) {
        return Optional.ofNullable(getFromCache(TmdbCacheEnum.PERSON_CACHE.getCacheKey(), cacheKey, PersonWithArtwork.class));
    }
}
