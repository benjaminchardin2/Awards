package com.bencha.webservices.api;

import com.bencha.db.generated.VerificationToken;
import com.bencha.enums.VerificationTokenType;
import com.bencha.services.UserService;
import com.bencha.services.VerificationService;
import com.bencha.webservices.errors.ProjectWsErrors;
import com.coreoz.plume.jersey.errors.WsException;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import com.google.common.base.Strings;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.Instant;

@Path("/verify-email")
@Tag(name = "verify email", description = "verify email")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PublicApi
@Singleton
public class VerificationWs {
    private final VerificationService verificationService;
    private final UserService userService;

    @Inject
    public VerificationWs(
        VerificationService verificationService,
        UserService userService
    ) {
        this.verificationService = verificationService;
        this.userService = userService;
    }

    @POST
    @Operation(description = "Validate account")
    public void verifyEmail(@QueryParam("token") String token) {
        if (Strings.isNullOrEmpty(token)) {
            throw new WsException(ProjectWsErrors.VERIFICATION_UNKNOWN);
        }
        VerificationToken verificationToken = verificationService.findVerificationToken(token, VerificationTokenType.EMAIL);
        if (verificationToken == null) {
            throw new WsException(ProjectWsErrors.VERIFICATION_UNKNOWN);
        }
        if (verificationToken.getExpirationDate().isBefore(Instant.now())) {
            verificationService.deleteToken(verificationToken);
            throw new WsException(ProjectWsErrors.VERIFICATION_UNKNOWN);
        }
        userService.validateAccount(verificationToken);
    }

    @POST
    @Path("/resend")
    @Operation(description = "Resend verification email")
    public void resendVerificationEmail(@QueryParam("email") String email) {
        verificationService.resendEmailVerificationEmail(email);
    }
}
