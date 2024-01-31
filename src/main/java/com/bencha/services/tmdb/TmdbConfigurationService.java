package com.bencha.services.tmdb;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.config.TmdbConfiguration;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TmdbConfigurationService {
    private static final String PREFERED_MEDIA_SIZE = "w342";
    private final String mediaUrl;
    private final String mediaSize;
    @Inject
    public TmdbConfigurationService(
        TmdbApi tmdbApi
    ) {
        TmdbConfiguration tmdbConfiguration = tmdbApi.getConfiguration();
        this.mediaUrl = tmdbConfiguration.getSecureBaseUrl();
        this.mediaSize = tmdbConfiguration.isValidPosterSize(PREFERED_MEDIA_SIZE) ? PREFERED_MEDIA_SIZE : tmdbConfiguration.getPosterSizes().stream().findFirst().get();
    }

    public String buildMediaUrl(String mediaPath) {
        return this.mediaUrl + this.mediaSize + mediaPath;
    }
}
