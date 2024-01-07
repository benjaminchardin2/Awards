package com.bencha.db.dao;

import com.bencha.db.generated.QUserParticipation;
import com.bencha.db.generated.UserParticipation;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;

import javax.inject.Singleton;

@Singleton
public class UserParticipationDao extends CrudDaoQuerydsl<UserParticipation> {
    public UserParticipationDao(TransactionManagerQuerydsl transactionManagerQuerydsl) {
        super(transactionManagerQuerydsl, QUserParticipation.userParticipation);
    }
}
