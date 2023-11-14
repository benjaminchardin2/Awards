package com.bencha.db.dao;

import com.bencha.db.generated.AwardNominee;
import com.bencha.db.generated.Ceremony;
import com.bencha.db.generated.QAwardNominee;
import com.bencha.db.generated.QCeremony;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;

import javax.inject.Singleton;

@Singleton
public class AwardNomineeDao extends CrudDaoQuerydsl<AwardNominee> {
    public AwardNomineeDao(TransactionManagerQuerydsl transactionManagerQuerydsl) {
        super(transactionManagerQuerydsl, QAwardNominee.awardNominee);
    }
}
