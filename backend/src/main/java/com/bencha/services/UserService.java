package com.bencha.services;

import com.bencha.db.dao.RoleDao;
import com.bencha.db.dao.UserDao;
import com.bencha.db.dao.VerificationTokenDao;
import com.bencha.db.generated.User;
import com.bencha.db.generated.VerificationToken;
import com.bencha.enums.Roles;
import com.coreoz.plume.admin.services.hash.HashService;
import com.google.common.base.Strings;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

@Singleton
public class UserService {
    private final HashService hashService;
    private final VerificationTokenDao verificationTokenDao;
    private final UserDao userDao;
    private final RoleDao roleDao;

    @Inject
    public UserService(
        UserDao userDao,
        RoleDao roleDao,
        VerificationTokenDao verificationTokenDao,
        HashService hashService
    ) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.verificationTokenDao = verificationTokenDao;
        this.hashService = hashService;
    }

    public void validateAccount(VerificationToken verificationToken) {
        User user = userDao.findById(verificationToken.getUserId());
        Long roleId = roleDao.findRoleIdByLabel(Roles.VERIFIED_USER.getLabel());
        user.setValidated(true);
        user.setIdRole(roleId);
        userDao.save(user);
        verificationTokenDao.delete(verificationToken.getId());
    }

    public void changePassword(String newPassword, VerificationToken verificationToken) {
        User user = userDao.findById(verificationToken.getUserId());
        user.setPassword(hashService.hashPassword(newPassword));
        userDao.save(user);
        verificationTokenDao.delete(verificationToken.getId());
    }

    public User findById(Long userId) {
        return userDao.findById(userId);
    }

    public Boolean checkPassword(Long userId, String password) {
        User user = userDao.findById(userId);
        if (!Strings.isNullOrEmpty(user.getGoogleSub())) {
            return true;
        }
        if (hashService.checkPassword(password, user.getPassword())) {
            return true;
        }
        return false;
    }

    public Boolean checkPassword(User user, String password) {
        if (hashService.checkPassword(password, user.getPassword())) {
            return true;
        }
        return false;
    }

    public void deleteUser(Long userId) {
        verificationTokenDao.deleteUsersVerificationTokens(userId);
        userDao.delete(userId);
    }

    public void modifyPassword(User user, String newPassword) {
        String newPasswordHashed = hashService.hashPassword(newPassword);
        user.setPassword(newPasswordHashed);
        userDao.save(user);
    }

    public String modifyUsername(Long userId, String userName) {
        User userFromDb = userDao.findById(userId);
        if (!userFromDb.getUserName().equals(userName)) {
            String userHashtag = getUserHashtagForUsername(userName);
            userFromDb.setUserName(userName);
            userFromDb.setUserHashtag(userHashtag);
            userDao.save(userFromDb);
            return userHashtag;
        }
        return userFromDb.getUserHashtag();
    }

    public String getUserHashtagForUsername(String userName) {
        List<String> tagsAlreadyAssigned = userDao.findUserHashtagsWithUserName(userName);
        return findTag((long) Math.floorDiv(tagsAlreadyAssigned.size(), 1000), tagsAlreadyAssigned);
    }

    private String findTag(Long minRange, List<String> tagsAlreadyAssigned) {
        long x = minRange + (long) (Math.random() * ((minRange + 1000) - minRange));

        String tag = "#" + String.format("%04d", x);
        while (tagsAlreadyAssigned.contains(tag)) {
            x = minRange + (long) (Math.random() * ((minRange + 1000) - minRange));
            tag = "#" + String.format("%04", x);
        }
        return tag;
    }
}
