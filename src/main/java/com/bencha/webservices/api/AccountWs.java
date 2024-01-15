package com.bencha.webservices.api;

import com.bencha.db.dao.UserDao;
import com.bencha.db.generated.User;
import com.bencha.services.UserService;
import com.bencha.webservices.beans.Profile;
import com.bencha.webservices.errors.ProjectWsErrors;
import com.coreoz.plume.admin.jersey.feature.RestrictToAdmin;
import com.coreoz.plume.admin.services.hash.HashService;
import com.coreoz.plume.admin.websession.WebSessionAdmin;
import com.coreoz.plume.jersey.errors.WsException;
import com.google.common.base.Strings;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/account")
@Tag(name = "profile", description = "Get Profile information")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RestrictToAdmin("VIEW_WEB")
@Singleton
public class AccountWs {
    private final UserService userService;

    @Inject
    public AccountWs(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Operation(description = "Get the connected user's profile")
    public Profile getProfile(
            @Context WebSessionAdmin webSessionAdmin
    ) {
        User user = userService.findById(webSessionAdmin.getIdUser());
        return Profile.of(
                user.getUserName(),
                user.getEmail(),
                user.getUserHashtag(),
            !Strings.isNullOrEmpty(user.getGoogleSub())
        );
    }

    @POST
    @Path("/delete")
    @Operation(description = "Delete the user's account")
    @Consumes(MediaType.TEXT_PLAIN)
    public void deleteAccount(
        @Context WebSessionAdmin webSessionAdmin,
        String password
    ) {
        Boolean result = userService.checkPassword(webSessionAdmin.getIdUser(), password);
        if (result) {
            userService.deleteUser(webSessionAdmin.getIdUser());
        } else {
            throw new WsException(ProjectWsErrors.INVALID_PASSWORD);
        }
    }

    @PUT
    @Path("/username")
    @Operation(description = "Modify the user's username")
    @Consumes(MediaType.TEXT_PLAIN)
    public String modifyUsername(
        @Context WebSessionAdmin webSessionAdmin,
        String username
    ) {
        return userService.modifyUsername(webSessionAdmin.getIdUser(), username);
    }
}
