package com.bencha.services;

import com.bencha.db.dao.RoleDao;
import com.bencha.db.dao.UserDao;
import com.bencha.db.dao.VerificationTokenDao;
import com.bencha.db.generated.User;
import com.bencha.db.generated.VerificationToken;
import com.bencha.enums.VerificationTokenType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Singleton
public class VerificationService {
    private final EmailService emailService;
    private final VerificationTokenDao verificationTokenDao;
    private final UserDao userDao;
    private final RoleDao roleDao;

    @Inject
    public VerificationService(
        EmailService emailService,
        VerificationTokenDao verificationTokenDao,
        UserDao userDao,
        RoleDao roleDao
    ) {
        this.emailService = emailService;
        this.verificationTokenDao = verificationTokenDao;
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    public void sendVerificationLink(User user) {
        VerificationToken verificationToken = createVerificationTokenAndSaveIt(user.getId(), VerificationTokenType.EMAIL);
        emailService.sendEmailVerificationEmail(user.getEmail(), verificationToken.getVerificationString());
    }

    public void sendResetPasswordLink(User user) {
        VerificationToken verificationToken = createVerificationTokenAndSaveIt(user.getId(), VerificationTokenType.PASSWORD);
        emailService.sendResetPasswordEmail(user.getEmail(), verificationToken.getVerificationString());
    }

    private VerificationToken createVerificationTokenAndSaveIt(Long userId, VerificationTokenType verificationTokenType) {
        String token = getRandomHexString(126);
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setVerificationString(token);
        verificationToken.setExpirationDate(Instant.now().plus(1, ChronoUnit.DAYS));
        verificationToken.setUserId(userId);
        verificationToken.setType(verificationTokenType.name());
        verificationTokenDao.save(verificationToken);
        return verificationToken;
    }

    private String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.substring(0, numchars);
    }

    public VerificationToken findVerificationToken(String token, VerificationTokenType tokenType) {
        return verificationTokenDao.findVerificationTokenByTokenAndTokenType(token, tokenType);
    }

    public void resendEmailVerificationEmail(String email) {
        Optional<User> optUser = userDao.findByEmail(email);
        if (optUser.isPresent()) {
            User user = optUser.get();
            if (!user.getValidated()) {
                Long count = verificationTokenDao.countUserVerificationTokenByType(user.getId(), VerificationTokenType.EMAIL);
                if (count.equals(0L)) {
                    sendVerificationLink(user);
                }
            }
        }
    }

    public void resetPasswordEmail(String email) {
        Optional<User> optUser = userDao.findByEmail(email);
        if (optUser.isPresent()) {
            User user = optUser.get();
            Long count = verificationTokenDao.countUserVerificationTokenByType(user.getId(), VerificationTokenType.PASSWORD);
            if (count.equals(0L)) {
                sendResetPasswordLink(user);
            }
        }
    }


    public void deleteToken(VerificationToken verificationToken) {
        verificationTokenDao.delete(verificationToken.getId());
    }

    public void deleteOutdatedVerificationTokens() {
        verificationTokenDao.deleteOutdatedVerificationTokens();
    }
}
