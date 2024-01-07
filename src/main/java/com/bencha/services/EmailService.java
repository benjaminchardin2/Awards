package com.bencha.services;

import com.bencha.services.configuration.ConfigurationService;
import com.bencha.util.ResourceFileProvider;
import lombok.extern.slf4j.Slf4j;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
@Slf4j
public class EmailService {

    private final Mailer mailer;
    private final ConfigurationService configurationService;

    @Inject
    public EmailService(Mailer mailer, ConfigurationService configurationService) {
        this.mailer = mailer;
        this.configurationService = configurationService;
    }

    public void sendEmailVerificationEmail(String toEmail, String verificationToken) {
        try {
            String html = ResourceFileProvider.getResourceFileAsString(configurationService.getVerificationLinkHtmlPath());
            String verificationLink = configurationService.getWebUrl() + configurationService.getVerificationLinkWebPath() + verificationToken;
            if (html != null) {
                String verificationLinkFinal = html.replace("${verification_link}", verificationLink);
                Email email = EmailBuilder
                    .startingBlank()
                    .to(toEmail)
                    .withSubject("Création de votre compte Rank-It")
                    .withHTMLText(verificationLinkFinal)
                    .buildEmail();
                mailer.sendMail(email, true);
            } else {
                logger.error("Unable to retrieve email verification file");
            }
        } catch (IOException e) {
            logger.error("Unable to retrieve email verification file");
        }
    }

    public void sendResetPasswordEmail(String toEmail, String verificationToken) {
        try {
            String html = ResourceFileProvider.getResourceFileAsString(configurationService.getResetPasswordHtmlPath());
            String verificationLink = configurationService.getWebUrl() + configurationService.getResetPasswordWebPath() + verificationToken;
            if (html != null) {
                String verificationLinkFinal = html.replace("${reset_link}", verificationLink);
                Email email = EmailBuilder
                    .startingBlank()
                    .to(toEmail)
                    .withSubject("Demande de modification de mot de passe Rank-it")
                    .withHTMLText(verificationLinkFinal)
                    .buildEmail();
                mailer.sendMail(email, true);
            } else {
                logger.error("Unable to retrieve reset password file");
            }
        } catch (IOException e) {
            logger.error("Unable to retrieve reset password file");
        }
    }
}
