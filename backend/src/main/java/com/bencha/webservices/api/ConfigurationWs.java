package com.bencha.webservices.api;

import com.bencha.services.LegalService;
import com.bencha.services.configuration.ConfigurationService;
import com.bencha.webservices.beans.GoogleConfiguration;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/configuration")
@Tag(name = "configuration", description = "Retrive configuration endpoints")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PublicApi
@Singleton
public class ConfigurationWs {
    private final ConfigurationService configurationService;

    @Inject
    public ConfigurationWs(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @GET
    @Path("/google")
    @Operation(description = "Get the google configuration")
    public GoogleConfiguration getGoogleConfiguration() {
        GoogleConfiguration googleConfiguration = new GoogleConfiguration();
        googleConfiguration.setClientId(configurationService.getGoogleClientId());
        googleConfiguration.setReCaptchaPublicKey(configurationService.getGoogleReCaptchaPublicKey());
        return googleConfiguration;
    }
}
