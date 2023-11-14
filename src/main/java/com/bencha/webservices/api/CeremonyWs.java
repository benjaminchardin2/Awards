package com.bencha.webservices.api;

import com.bencha.db.generated.Ceremony;
import com.bencha.services.CeremonyService;
import com.bencha.webservices.beans.CeremonyRequest;
import com.bencha.webservices.beans.PaginatedRequest;
import com.bencha.webservices.beans.PaginatedResponse;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/ceremonies")
@Tag(name = "ceremonies", description = "Interactions linked to ceremonies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PublicApi
@Singleton
public class CeremonyWs {
    private static final Integer DEFAULT_LIMIT = 4;
    private final CeremonyService ceremonyService;

    @Inject
    public CeremonyWs(CeremonyService ceremonyService) {
        this.ceremonyService = ceremonyService;
    }

    @GET
    @Operation(description = "Search ceremonies")
    public PaginatedResponse<Ceremony> searchCeremonies(
        @Parameter(required = false) @QueryParam("offset") Integer offset,
        @Parameter(required = false) @QueryParam("limit") Integer limit,
        @Parameter(required = false) @QueryParam("name") String name) {
        PaginatedRequest<CeremonyRequest> ceremonyRequest = PaginatedRequest.of(
            offset != null ? offset : 0,
            limit != null ? limit : DEFAULT_LIMIT,
            CeremonyRequest.of(name)
        );
        return ceremonyService.searchCeremonies(ceremonyRequest);
    }

    @GET
    @Path("/highlighted")
    @Operation(description = "Get the top two ceremonies highlighted")
    public List<Ceremony> findTop2Ceremonies() {
        return ceremonyService.findTop2Ceremonies();
    }
}
