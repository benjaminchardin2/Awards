package com.bencha.webservices.api;

import com.bencha.services.RegistrationService;
import com.bencha.webservices.beans.AccountCreationRequest;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/register")
@Tag(name = "registration", description = "Registering users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PublicApi
@Singleton
public class RegistrationWs {
    private final RegistrationService registrationService;

    @Inject
    public RegistrationWs(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @POST
    @Operation(description = "Register a user")
    public void registerUser(AccountCreationRequest accountCreationRequest) {
        registrationService.registerUser(accountCreationRequest);
    }
}
