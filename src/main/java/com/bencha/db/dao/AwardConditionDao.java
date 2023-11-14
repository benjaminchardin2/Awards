package com.bencha.db.dao;

import com.bencha.db.generated.AwardCondition;
import com.bencha.db.generated.QAwardCondition;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;

import javax.inject.Singleton;

@Singleton
public class AwardConditionDao extends CrudDaoQuerydsl<AwardCondition> {
    public AwardConditionDao(TransactionManagerQuerydsl transactionManagerQuerydsl) {
        super(transactionManagerQuerydsl, QAwardCondition.awardCondition);
    }
}
