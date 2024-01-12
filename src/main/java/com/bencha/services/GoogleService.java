package com.bencha.services;

import com.bencha.api.google.GoogleApiService;
import com.bencha.api.google.beans.CaptchaVerifyResponse;
import com.bencha.db.dao.UserDao;
import com.bencha.db.generated.User;
import com.bencha.services.configuration.ConfigurationService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

@Singleton
@Slf4j
public class GoogleService {
    private final UserDao userDao;
    private final ConfigurationService configurationService;
    private final GoogleApiService googleApiService;

    @Inject
    public GoogleService(
        UserDao userDao,
        ConfigurationService configurationService,
        GoogleApiService googleApiService
    ) {
        this.userDao = userDao;
        this.configurationService = configurationService;
        this.googleApiService = googleApiService;
    }

    public GoogleIdToken authenticateUserUsingGoogleCredentials(String credentials) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(configurationService.getGoogleClientId()))
                    .build();
            return verifier.verify(credentials.replaceAll("\"", ""));
        } catch (GeneralSecurityException | IOException e) {
            logger.error("Unable to verify google token", e);
            return null;
        }
    }

    public Optional<User> findUserByGoogleSub(String sub) {
        return userDao.findByGoogleSub(sub);
    }

    public Boolean isCaptchaOk(String captchaResponse) {
        CaptchaVerifyResponse captchaVerifyResponse = this.googleApiService.verifyCaptchaResponse(
            this.configurationService.getGoogleReCaptchaPrivateKey(),
            captchaResponse
        );
        if (captchaVerifyResponse.getHostname() != null && captchaVerifyResponse.getHostname().equals(configurationService.getWebHost())) {
            return captchaVerifyResponse.getSuccess();
        }
        return false;
    }
}
