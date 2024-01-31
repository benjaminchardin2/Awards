package com.bencha.db.dao;

import com.bencha.db.generated.Pronostic;
import com.bencha.db.generated.QPronostic;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class PronosticDao extends CrudDaoQuerydsl<Pronostic> {
    @Inject
    public PronosticDao(TransactionManagerQuerydsl transactionManagerQuerydsl) {
        super(transactionManagerQuerydsl, QPronostic.pronostic);
    }

    public Pronostic findUserPronosticsForAward(Long userParticipationId, Long awardId) {
      return transactionManager
          .selectQuery()
          .select(QPronostic.pronostic)
          .from(QPronostic.pronostic)
          .where(
              QPronostic.pronostic.awardId.eq(awardId)
                  .and(QPronostic.pronostic.userParticipationId.eq(userParticipationId))
          )
          .fetchOne();
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
}
