package com.bencha.db.dao;

import com.bencha.db.generated.Pronostic;
import com.bencha.db.generated.QAward;
import com.bencha.db.generated.QAwardNominee;
import com.bencha.db.generated.QPronostic;
import com.bencha.enums.PronosticType;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;
import com.querydsl.core.Tuple;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class PronosticDao extends CrudDaoQuerydsl<Pronostic> {
    @Inject
    public PronosticDao(TransactionManagerQuerydsl transactionManagerQuerydsl) {
        super(transactionManagerQuerydsl, QPronostic.pronostic);
    }

    public Pronostic findUserPronosticsForAwardAndPronosticType(Long userParticipationId, Long awardId, PronosticType pronosticType) {
      return transactionManager
          .selectQuery()
          .select(QPronostic.pronostic)
          .from(QPronostic.pronostic)
          .where(
              QPronostic.pronostic.awardId.eq(awardId)
                  .and(QPronostic.pronostic.type.eq(pronosticType.name()))
                  .and(QPronostic.pronostic.userParticipationId.eq(userParticipationId))
          )
          .fetchOne();
    }

    public List<Pronostic> findUserPronosticsForAward(Long userParticipationId, Long awardId) {
        return transactionManager
            .selectQuery()
            .select(QPronostic.pronostic)
            .from(QPronostic.pronostic)
            .where(
                QPronostic.pronostic.awardId.eq(awardId)
                    .and(QPronostic.pronostic.userParticipationId.eq(userParticipationId))
            )
            .fetch();
    }

    public List<Pronostic> findUserPronosticsForParticipation(Long userParticipationId) {
        return transactionManager
            .selectQuery()
            .select(QPronostic.pronostic)
            .from(QPronostic.pronostic)
            .where(
                QPronostic.pronostic.userParticipationId
                    .eq(userParticipationId)
            )
            .fetch();
    }

    public List<Tuple> findUserPronosticsWithNomineeAndAwardForParticipation(Long userParticipationId) {
        return transactionManager
            .selectQuery()
            .select(QPronostic.pronostic, QAwardNominee.awardNominee, QAward.award)
            .from(QPronostic.pronostic)
            .join(QAward.award)
            .on(QAward.award.id.eq(QPronostic.pronostic.awardId))
            .leftJoin(QAwardNominee.awardNominee)
            .on(QAwardNominee.awardNominee.id.eq(QPronostic.pronostic.nomineeId))
            .where(
                QPronostic.pronostic.userParticipationId
                    .eq(userParticipationId)
            )
            .orderBy(QAward.award.id.asc())
            .fetch();
    }
}
