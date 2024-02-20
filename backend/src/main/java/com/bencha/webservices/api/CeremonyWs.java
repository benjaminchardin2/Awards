package com.bencha.webservices.api;

import com.bencha.db.generated.Ceremony;
import com.bencha.services.AwardService;
import com.bencha.services.CeremonyService;
import com.bencha.services.PronosticService;
import com.bencha.webservices.beans.*;
import com.coreoz.plume.admin.websession.WebSessionAdmin;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
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
    private final AwardService awardService;
    private final PronosticService pronosticService;

    @Inject
    public CeremonyWs(CeremonyService ceremonyService, AwardService awardService, PronosticService pronosticService) {
        this.ceremonyService = ceremonyService;
        this.awardService = awardService;
        this.pronosticService = pronosticService;
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

    @GET
    @Path("/{id}/awards")
    @Operation(description = "Get the top two ceremonies highlighted")
    public List<AwardWithNominees> findCeremonyAwards(@PathParam("id") Long ceremonyId) {
        return awardService.findCeremonyAwardsDto(ceremonyId);
    }

    @GET
    @Path("/{id}/pronostics")
    @Operation(description = "Get the top two ceremonies highlighted")
    public CeremonyPronostics findCeremonyPronostics(@PathParam("id") Long ceremonyId, @Context WebSessionAdmin webSessionAdmin) {
        return pronosticService.findUserPronostics(webSessionAdmin.getIdUser(), ceremonyId);
    }

    @POST
    @Path("/{id}/pronostics")
    @Operation(description = "Get the top two ceremonies highlighted")
    public void saveCeremonyPronostic(@PathParam("id") Long ceremonyId, @Context WebSessionAdmin webSessionAdmin, PronosticChoice pronosticChoice) {
        if (webSessionAdmin != null) {
            pronosticService.saveUserChoice(webSessionAdmin.getIdUser(), ceremonyId, pronosticChoice);
        } else {
            pronosticService.saveAnonymousChoice(pronosticChoice);
        }
    }

    @POST
    @Path("/{id}/pronostics/remove")
    @Operation(description = "Get the top two ceremonies highlighted")
    public void removeCeremonyPronostics(@PathParam("id") Long ceremonyId, @Context WebSessionAdmin webSessionAdmin, AwardDeletion awardDeletion) {
        if (webSessionAdmin != null) {
            pronosticService.removeCeremonyPronostics(webSessionAdmin.getIdUser(), ceremonyId, awardDeletion);
        } else {
            pronosticService.removeAnonymousCeremonyPronostics(awardDeletion);
        }
    }

    @POST
    @Path("/{id}/pronostics/link")
    @Operation(description = "Link pronostics to user")
    public void linkPronosticsToUser(@PathParam("id") Long ceremonyId, @Context WebSessionAdmin webSessionAdmin, CeremonyPronostics ceremonyPronostics) {
        pronosticService.linkPronosticsToUser(ceremonyId, webSessionAdmin.getIdUser(), ceremonyPronostics);
    }

    @POST
    @Path("/{id}/anonymous-pronostics")
    @Operation(description = "Save pronostics")
    public SerializedId saveAnonymousPronostics(@PathParam("id") Long ceremonyId, CeremonyPronostics ceremonyPronostics) {
        return SerializedId.of(pronosticService.createAnonymousParticipation(ceremonyId, ceremonyPronostics));
    }

    @POST
    @Path("/{id}/share-link")
    @Consumes(MediaType.TEXT_PLAIN)
    @Operation(description = "Get the share link")
    public String getPronosticsShareLink(@PathParam("id") Long ceremonyId, @Context WebSessionAdmin webSessionAdmin, Long participationId) {
        if (webSessionAdmin != null) {
            return pronosticService.getPronosticsShareLink(webSessionAdmin.getIdUser(), ceremonyId);
        } else {
            return pronosticService.getPronosticsShareLink(participationId);
        }
    }
}
