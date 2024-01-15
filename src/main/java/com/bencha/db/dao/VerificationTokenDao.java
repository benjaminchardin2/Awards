package com.bencha.db.dao;

import com.bencha.db.generated.QVerificationToken;
import com.bencha.db.generated.VerificationToken;
import com.bencha.enums.VerificationTokenType;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;

@Singleton
public class VerificationTokenDao extends CrudDaoQuerydsl<VerificationToken> {
    @Inject
    public VerificationTokenDao(TransactionManagerQuerydsl transactionManagerQuerydsl) {
        super(transactionManagerQuerydsl, QVerificationToken.verificationToken);
    }

    public VerificationToken findVerificationTokenByTokenAndTokenType(String token, VerificationTokenType verificationTokenType) {
        return transactionManager
            .selectQuery()
            .select(QVerificationToken.verificationToken)
            .from(QVerificationToken.verificationToken)
            .where(
                QVerificationToken.verificationToken.verificationString.eq(token)
                    .and(QVerificationToken.verificationToken.type.eq(verificationTokenType.name()))
            )
            .fetchOne();
    }

    public Long countUserVerificationTokenByType(Long userId, VerificationTokenType verificationTokenType) {
        return transactionManager
            .selectQuery()
            .select(QVerificationToken.verificationToken)
            .from(QVerificationToken.verificationToken)
            .where(
                QVerificationToken.verificationToken.userId.eq(userId)
                    .and(QVerificationToken.verificationToken.type.eq(verificationTokenType.name()))
            )
            .fetchCount();
    }

    public void deleteOutdatedVerificationTokens() {
        transactionManager
            .delete(QVerificationToken.verificationToken)
            .where(QVerificationToken.verificationToken.expirationDate.before(Instant.now()))
            .execute();
    }

    public void deleteUsersVerificationTokens(Long userId) {
        transactionManager
            .delete(QVerificationToken.verificationToken)
            .where(QVerificationToken.verificationToken.userId.eq(userId))
            .execute();
    }
}
