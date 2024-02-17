package com.bencha.db.dao;

import com.bencha.db.generated.QTranslation;
import com.bencha.db.generated.Translation;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class TranslationDao extends CrudDaoQuerydsl<Translation> {
    @Inject
    public TranslationDao(TransactionManagerQuerydsl transactionManagerQuerydsl) {
        super(transactionManagerQuerydsl, QTranslation.translation);
    }

    public Optional<String> findTranslation(String source) {
        return Optional.ofNullable(transactionManager
            .selectQuery()
            .select(QTranslation.translation.translationValue)
            .from(QTranslation.translation)
            .where(QTranslation.translation.source.eq(source))
            .fetchFirst());
    }
}
