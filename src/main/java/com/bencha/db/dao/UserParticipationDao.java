package com.bencha.db.dao;

import com.bencha.db.generated.QUserParticipation;
import com.bencha.db.generated.UserParticipation;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserParticipationDao extends CrudDaoQuerydsl<UserParticipation> {
    @Inject
    public UserParticipationDao(TransactionManagerQuerydsl transactionManagerQuerydsl) {
        super(transactionManagerQuerydsl, QUserParticipation.userParticipation);
    }

    public UserParticipation findUserParticipation(Long userId, Long ceremonyId) {
        return transactionManager
            .selectQuery()
            .select(QUserParticipation.userParticipation)
            .from(QUserParticipation.userParticipation)
            .where(
                QUserParticipation.userParticipation.userId.eq(userId)
                    .and(QUserParticipation.userParticipation.ceremonyId.eq(ceremonyId))
            )
            .fetchOne();
    }
}
