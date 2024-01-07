package com.bencha.db.dao;

import com.bencha.db.generated.Legal;
import com.bencha.db.generated.QLegal;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LegalDao extends CrudDaoQuerydsl<Legal> {
    @Inject
    public LegalDao(TransactionManagerQuerydsl transactionManagerQuerydsl) {
        super(transactionManagerQuerydsl, QLegal.legal);
    }
}
