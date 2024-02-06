package com.bencha.jersey;

import com.bencha.services.configuration.ConfigurationService;
import info.movito.themoviedbapi.TmdbApi;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * TMDB API
 */
@Singleton
public class TmdbApiProvider implements Provider<TmdbApi> {

	private final TmdbApi tmdbApi;

	@Inject
	public TmdbApiProvider(ConfigurationService configurationService) {
		tmdbApi = new TmdbApi(configurationService.getTmdbApiKey());
	}

	@Override
	public TmdbApi get() {
		return tmdbApi;
	}

}
