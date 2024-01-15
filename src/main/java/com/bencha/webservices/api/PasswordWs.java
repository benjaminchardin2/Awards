package com.bencha.webservices.api;

import com.bencha.db.generated.User;
import com.bencha.db.generated.VerificationToken;
import com.bencha.enums.VerificationTokenType;
import com.bencha.services.GoogleService;
import com.bencha.services.UserService;
import com.bencha.services.VerificationService;
import com.bencha.webservices.beans.NewPassword;
import com.bencha.webservices.beans.PasswordModificationRequest;
import com.bencha.webservices.beans.Recaptcha;
import com.bencha.webservices.errors.ProjectWsErrors;
import com.coreoz.plume.admin.websession.WebSessionAdmin;
import com.coreoz.plume.jersey.errors.WsException;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import com.google.common.base.Strings;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.time.Instant;

@Path("/passwords")
@Tag(name = "Reset passwords", description = "Reset passwords actions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PublicApi
@Singleton
public class PasswordWs {
    private final VerificationService verificationService;
    private final UserService userService;
    private final GoogleService googleService;

    @Inject
    public PasswordWs(
        VerificationService verificationService,
        UserService userService,
        GoogleService googleService
    ) {
        this.verificationService = verificationService;
        this.userService = userService;
        this.googleService = googleService;
    }

    @POST
    @Operation(description = "Modify password")
    public void modifyPassword(PasswordModificationRequest passwordModificationRequest) {
        if (
            passwordModificationRequest == null
                || Strings.isNullOrEmpty(passwordModificationRequest.getToken())
                || Strings.isNullOrEmpty(passwordModificationRequest.getNewPassword())
        ) {
            throw new WsException(ProjectWsErrors.PASSWORD_MODIFICATION_UNKNOWN);
        }
        VerificationToken verificationToken = verificationService.findVerificationToken(passwordModificationRequest.getToken(), VerificationTokenType.PASSWORD);
        if (verificationToken == null) {
            throw new WsException(ProjectWsErrors.PASSWORD_MODIFICATION_UNKNOWN);
        }
        if (verificationToken.getExpirationDate().isBefore(Instant.now())) {
            verificationService.deleteToken(verificationToken);
            throw new WsException(ProjectWsErrors.PASSWORD_MODIFICATION_UNKNOWN);
        }
        userService.changePassword(passwordModificationRequest.getNewPassword(), verificationToken);
    }

    @POST
    @Path("/reset")
    @Operation(description = "Send password reset email")
    public void sendResetPasswordEmail(@QueryParam("email") String email, @RequestBody Recaptcha recaptchaResponse) {
        if (!googleService.isCaptchaOk(recaptchaResponse.getReCaptchaResponse())) {
            throw new WsException(ProjectWsErrors.RECAPTCHA_ERROR);
        }
        verificationService.resetPasswordEmail(email);
    }

    @PUT
    @Path("/modify")
    @Operation(description = "Modify user's password")
    public void modifyPassword(@Context WebSessionAdmin webSessionAdmin, NewPassword newPassword) {
        User user = userService.findById(webSessionAdmin.getIdUser());
        if (userService.checkPassword(user, newPassword.getOldPassword())) {
            userService.modifyPassword(user, newPassword.getNewPassword());
        } else {
            throw new WsException(ProjectWsErrors.INVALID_PASSWORD);
        }
    }
}
