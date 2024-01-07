package com.bencha.services;

import com.bencha.db.dao.UserDao;
import com.bencha.webservices.beans.AuthenticatedSimpleUser;
import com.coreoz.plume.admin.services.hash.HashService;
import com.coreoz.plume.admin.services.role.AdminRoleService;
import com.google.common.collect.ImmutableSet;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class LoginService {
    private final UserDao userDao;
    private final AdminRoleService adminRoleService;
    private final HashService hashService;

    @Inject
    public LoginService(UserDao userDao, AdminRoleService adminRoleService, HashService hashService) {
        this.userDao = userDao;
        this.hashService = hashService;
        this.adminRoleService = adminRoleService;
    }

    public Optional<AuthenticatedSimpleUser> authenticate(String email, String password) {
        return userDao
            .findByEmail(email)
            .filter(user -> hashService.checkPassword(password, user.getPassword()))
            .map(user -> AuthenticatedSimpleUser.of(
                user,
                ImmutableSet.copyOf(adminRoleService.findRolePermissions(user.getIdRole()))
            ));
    }
}
