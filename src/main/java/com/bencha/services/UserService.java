package com.bencha.services;

import com.bencha.db.dao.RoleDao;
import com.bencha.db.dao.UserDao;
import com.bencha.db.dao.VerificationTokenDao;
import com.bencha.db.generated.User;
import com.bencha.db.generated.VerificationToken;
import com.bencha.enums.Roles;
import com.coreoz.plume.admin.services.hash.HashService;

import javax.inject.Inject;
import javax.inject.Singleton;

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
}
