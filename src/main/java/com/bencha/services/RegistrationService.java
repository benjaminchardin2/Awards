package com.bencha.services;

import com.bencha.db.dao.UserDao;
import com.bencha.db.generated.User;
import com.bencha.webservices.beans.AccountCreationRequest;
import com.bencha.webservices.errors.ProjectWsErrors;
import com.coreoz.plume.admin.services.hash.HashService;
import com.coreoz.plume.admin.services.user.AdminUserService;
import com.coreoz.plume.admin.webservices.validation.AdminWsError;
import com.coreoz.plume.jersey.errors.Validators;
import com.coreoz.plume.jersey.errors.WsException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;

@Singleton
public class RegistrationService {
    private final UserDao userDao;
    private final AdminUserService adminUserService;
    private final HashService hashService;
    private final VerificationService verificationService;
    private final GoogleService googleService;

    @Inject
    public RegistrationService(UserDao userDao, AdminUserService adminUserService, HashService hashService, VerificationService verificationService, GoogleService googleService) {
        this.adminUserService = adminUserService;
        this.userDao = userDao;
        this.hashService = hashService;
        this.verificationService = verificationService;
        this.googleService = googleService;
    }

    public void registerUser(AccountCreationRequest accountCreationRequest) {
        Validators.checkRequired("email", accountCreationRequest.getEmail());
        Validators.checkRequired("mot de passe", accountCreationRequest.getPassword());
        Validators.checkRequired("username", accountCreationRequest.getUserName());
        Validators.checkRequired("rgpd", accountCreationRequest.getRgpd());

        if (!googleService.isCaptchaOk(accountCreationRequest.getRecaptcha())) {
            throw new WsException(ProjectWsErrors.RECAPTCHA_ERROR);
        }

        if (adminUserService.existsWithEmail(-1L, accountCreationRequest.getEmail())) {
            throw new WsException(AdminWsError.EMAIL_ALREADY_EXISTS);
        }
        User user = new User();
        user.setCreationDate(Instant.now());
        user.setEmail(accountCreationRequest.getEmail());
        user.setUserName(accountCreationRequest.getUserName());
        user.setPassword(hashService.hashPassword(accountCreationRequest.getPassword()));
        user.setIdRole(1L);
        user.setRgpdOk(accountCreationRequest.getRgpd());
        user.setValidated(false);
        user = userDao.save(user);
        verificationService.sendVerificationLink(user);
    }

    public User registerUserWithGoogleIdToken(GoogleIdToken.Payload payload) {
        User user = new User();
        user.setCreationDate(Instant.now());
        user.setEmail(payload.getEmail());
        user.setUserName((String) payload.get("name"));
        user.setGoogleSub(payload.getSubject());
        user.setIdRole(1L);
        user.setRgpdOk(true);
        user.setValidated(true);
        user = userDao.save(user);
        return user;
    }
}
