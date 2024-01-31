package com.bencha.db.dao;

import com.bencha.db.generated.AwardNominee;
import com.bencha.db.generated.QAwardNominee;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class AwardNomineeDao extends CrudDaoQuerydsl<AwardNominee> {

    @Inject
    public AwardNomineeDao(TransactionManagerQuerydsl transactionManagerQuerydsl) {
        super(transactionManagerQuerydsl, QAwardNominee.awardNominee);
    }

    public List<AwardNominee> findAwardsAwardNominees(List<Long> awardIds) {
        return this.transactionManager
            .selectQuery()
            .select(QAwardNominee.awardNominee)
            .from(QAwardNominee.awardNominee)
            .where(QAwardNominee.awardNominee.awardId.in(awardIds))
            .fetch();
    }
}
