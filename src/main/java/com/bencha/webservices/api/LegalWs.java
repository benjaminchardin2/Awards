package com.bencha.webservices.api;

import com.bencha.services.LegalService;
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

@Path("/legal")
@Tag(name = "legal", description = "Interactions linked to legal pages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PublicApi
@Singleton
public class LegalWs {
    private final LegalService legalService;

    @Inject
    public LegalWs(LegalService legalService) {
        this.legalService = legalService;
    }

    @GET
    @Operation(description = "Get the legal pages content")
    public Map<String, String> getLegalPages() {
        return legalService.getLegalPages();
    }
}
