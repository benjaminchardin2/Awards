package com.bencha.webservices.api;

import com.bencha.services.ShareService;
import com.bencha.webservices.beans.CeremonyShare;
import com.bencha.webservices.errors.ProjectWsErrors;
import com.coreoz.plume.jersey.errors.WsException;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/share")
@Tag(name = "share", description = "Retrieve share information")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PublicApi
@Singleton
public class ShareWs {
    private static final Integer DEFAULT_LIMIT = 4;
    private final ShareService shareService;
    @Inject
    public ShareWs(ShareService shareService) {
        this.shareService = shareService;
    }

    @GET
    @Path("/{share-code}")
    @Operation(description = "Get shared information from shareCode")
    public CeremonyShare findCeremonyShare(@PathParam("share-code") String shareCode) {
        CeremonyShare ceremonyShare = shareService.findCeremonyShare(shareCode);
        if (ceremonyShare == null) {
            throw new WsException(ProjectWsErrors.SHARE_NOT_FOUND);
        }
        return ceremonyShare;
    }
}
