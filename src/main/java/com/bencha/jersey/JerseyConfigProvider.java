package com.bencha.jersey;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import com.coreoz.plume.admin.jersey.feature.AdminSecurityFeature;
import com.coreoz.plume.admin.jersey.feature.RestrictToAdmin;
import org.glassfish.jersey.server.ResourceConfig;

import com.coreoz.plume.jersey.errors.WsJacksonJsonProvider;
import com.coreoz.plume.jersey.errors.WsResultExceptionMapper;
import com.coreoz.plume.jersey.java8.TimeParamProvider;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import com.coreoz.plume.jersey.security.permission.RequireExplicitAccessControlFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jersey configuration
 */
@Singleton
public class JerseyConfigProvider implements Provider<ResourceConfig> {

	private final ResourceConfig config;

	@Inject
	public JerseyConfigProvider(ObjectMapper objectMapper) {
		config = new ResourceConfig();

        // require explicit access control on API
        config.register(RequireExplicitAccessControlFeature.accessControlAnnotations(PublicApi.class, RestrictToAdmin.class));

        // this package will be scanned by Jersey to discover web-service classes
		config.packages("com.bencha.webservices");

        config.packages("com.coreoz.plume.admin.webservices");

		// filters configuration
		// handle errors and exceptions
		config.register(WsResultExceptionMapper.class);
		// to debug web-service requests
		// register(LoggingFeature.class);

		// java 8
		config.register(TimeParamProvider.class);

        config.register(AdminSecurityFeature.class);

		// WADL is like swagger for jersey
		// by default it should be disabled to prevent leaking API documentation
		config.property("jersey.config.server.wadl.disableWadl", true);

		// Disable automatique relative location URI resolution
		// By default it transform a relative location to an absolute location
		config.property("jersey.config.server.headers.location.relative.resolution.disabled", true);

		// jackson mapper configuration
		WsJacksonJsonProvider jacksonProvider = new WsJacksonJsonProvider();
		jacksonProvider.setMapper(objectMapper);
		config.register(jacksonProvider);
	}

	@Override
	public ResourceConfig get() {
		return config;
	}

}
