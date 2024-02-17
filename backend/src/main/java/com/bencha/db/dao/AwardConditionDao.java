package com.bencha.db.dao;

import com.bencha.db.generated.AwardCondition;
import com.bencha.db.generated.QAwardCondition;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class AwardConditionDao extends CrudDaoQuerydsl<AwardCondition> {
    @Inject
    public AwardConditionDao(TransactionManagerQuerydsl transactionManagerQuerydsl) {
        super(transactionManagerQuerydsl, QAwardCondition.awardCondition);
    }

    public List<AwardCondition> findByAwardIdAndConditionNames(Long awardId, List<String> conditionNames) {
        return transactionManager
            .selectQuery()
            .select(QAwardCondition.awardCondition)
            .from(QAwardCondition.awardCondition)
            .where(
                QAwardCondition.awardCondition.awardId.eq(awardId)
                    .and(QAwardCondition.awardCondition.conditionName.in(conditionNames))
            )
            .fetch();
    }
}
