package com.bencha.db.dao;

import com.bencha.db.generated.QCeremony;
import com.bencha.db.generated.QUserParticipation;
import com.bencha.db.generated.UserParticipation;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;
import com.querydsl.core.Tuple;

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

    public String getShareCodeByUserIdCeremonyId(Long userId, Long ceremonyId) {
        return transactionManager
            .selectQuery()
            .select(QUserParticipation.userParticipation.shareCode)
            .from(QUserParticipation.userParticipation)
            .where(
                QUserParticipation.userParticipation.userId.eq(userId)
                    .and(QUserParticipation.userParticipation.ceremonyId.eq(ceremonyId))
            )
            .fetchOne();
    }

    public String getShareCodeByParticipationId(Long participationId) {
        return transactionManager
            .selectQuery()
            .select(QUserParticipation.userParticipation.shareCode)
            .from(QUserParticipation.userParticipation)
            .where(QUserParticipation.userParticipation.id.eq(participationId))
            .fetchOne();
    }

    public Tuple findUserParticipationWithCeremonyNameByShareCode(String shareCode) {
        return transactionManager
            .selectQuery()
            .select(QUserParticipation.userParticipation, QCeremony.ceremony.name)
            .from(QUserParticipation.userParticipation)
            .join(QCeremony.ceremony)
            .on(QCeremony.ceremony.id.eq(QUserParticipation.userParticipation.ceremonyId))
            .where(
                QUserParticipation.userParticipation.shareCode.eq(shareCode)
            )
            .fetchOne();
    }
}
