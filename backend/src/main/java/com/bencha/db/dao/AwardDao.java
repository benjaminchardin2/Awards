package com.bencha.db.dao;

import com.bencha.db.generated.Award;
import com.bencha.db.generated.QAward;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class AwardDao extends CrudDaoQuerydsl<Award> {
    @Inject
    public AwardDao(TransactionManagerQuerydsl transactionManagerQuerydsl) {
        super(transactionManagerQuerydsl, QAward.award);
    }

    public List<Award> findAwardsByCeremonyId(Long ceremonyId) {
        return transactionManager
            .selectQuery()
            .select(QAward.award)
            .from(QAward.award)
            .where(QAward.award.ceremonyId.eq(ceremonyId))
            .fetch();
    }
}
