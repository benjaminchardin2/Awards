package com.bencha.db.dao;

import com.bencha.db.generated.QUser;
import com.bencha.db.generated.User;
import com.coreoz.plume.admin.db.generated.QAdminUser;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;
import com.querydsl.sql.SQLExpressions;

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

    public Optional<User> findByGoogleSub(String googleSub) {
        return Optional.ofNullable(
            transactionManager
                .selectQuery()
                .select(QUser.user)
                .from(QUser.user)
                .where(QUser.user.googleSub.eq(googleSub))
                .fetchOne()
        );
    }

    public Boolean existsWithSub(String googleSub) {
        return transactionManager
            .selectQuery()
            .select(SQLExpressions.selectOne())
            .from(QUser.user)
            .where(QUser.user.googleSub.eq(googleSub))
            .fetchOne() != null;
    }
}
