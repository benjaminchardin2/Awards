package com.bencha.db.dao;

import com.bencha.db.generated.AwardCondition;
import com.bencha.db.generated.Pronostic;
import com.bencha.db.generated.QAwardCondition;
import com.bencha.db.generated.QPronostic;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;

import javax.inject.Singleton;

@Singleton
public class PronosticDao extends CrudDaoQuerydsl<Pronostic> {
    public PronosticDao(TransactionManagerQuerydsl transactionManagerQuerydsl) {
        super(transactionManagerQuerydsl, QPronostic.pronostic);
    }
}
