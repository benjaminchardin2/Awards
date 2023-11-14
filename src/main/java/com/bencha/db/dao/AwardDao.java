package com.bencha.db.dao;

import com.bencha.db.generated.Award;
import com.bencha.db.generated.QAward;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;

import javax.inject.Singleton;

@Singleton
public class AwardDao extends CrudDaoQuerydsl<Award> {
    public AwardDao(TransactionManagerQuerydsl transactionManagerQuerydsl) {
        super(transactionManagerQuerydsl, QAward.award);
    }
}
