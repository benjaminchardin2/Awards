package com.bencha.db.dao;

import com.bencha.db.generated.QUser;
import com.bencha.db.generated.User;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class UserDao extends CrudDaoQuerydsl<User> {
    @Inject
    public UserDao(TransactionManagerQuerydsl transactionManagerQuerydsl) {
        super(transactionManagerQuerydsl, QUser.user);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(
            transactionManager
            .selectQuery()
            .select(QUser.user)
            .from(QUser.user)
            .where(QUser.user.email.eq(email))
            .fetchOne()
        );
    }
}
