package com.bencha.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

@Getter
@AllArgsConstructor
public enum TmdbCacheEnum {
    MOVIE_CACHE("NOMINEE_CACHE", 400, Duration.ofDays(1L)),
    PERSON_CACHE("PERSON_CACHE", 200, Duration.ofDays(1L));

    private final String cacheKey;
    private final int size;
    private final Duration duration;
}
