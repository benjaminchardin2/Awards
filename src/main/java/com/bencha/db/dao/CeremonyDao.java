package com.bencha.db.dao;

import com.bencha.db.generated.Ceremony;
import com.bencha.db.generated.QCeremony;
import com.bencha.db.generated.QUserParticipation;
import com.bencha.webservices.beans.CeremonyRequest;
import com.bencha.webservices.beans.PaginatedRequest;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;
import com.querydsl.sql.SQLQuery;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class CeremonyDao extends CrudDaoQuerydsl<Ceremony> {
    @Inject
    public CeremonyDao(TransactionManagerQuerydsl transactionManagerQuerydsl) {
        super(transactionManagerQuerydsl, QCeremony.ceremony);
    }

    private SQLQuery<Ceremony> applyConditionsFromRequest(SQLQuery<Ceremony> query, CeremonyRequest ceremonyRequest) {
        if (ceremonyRequest.getNameSearch() != null) {
            query = query.where(QCeremony.ceremony.name.like(ceremonyRequest.getNameSearch()));
        }
        return query;
    }

    public List<Ceremony> searchCeremonies(PaginatedRequest<CeremonyRequest> ceremonySearch) {
        SQLQuery<Ceremony> query = transactionManager
            .selectQuery()
            .select(QCeremony.ceremony)
            .from(QCeremony.ceremony)
            .leftJoin(QUserParticipation.userParticipation)
            .on(QUserParticipation.userParticipation.ceremonyId.eq(QCeremony.ceremony.id));
        query = applyConditionsFromRequest(query, ceremonySearch.getRequest());
        return query
            .groupBy(QCeremony.ceremony)
            .orderBy(QCeremony.ceremony.isHighlighted.desc(), QUserParticipation.userParticipation.count().desc())
            .offset(ceremonySearch.getOffset())
            .limit(ceremonySearch.getPageSize())
            .fetch();
    }

    public Long countCeremonies(PaginatedRequest<CeremonyRequest> ceremonySearch) {
        SQLQuery<Ceremony> query = transactionManager
            .selectQuery()
            .select(QCeremony.ceremony)
            .from(QCeremony.ceremony)
            .leftJoin(QUserParticipation.userParticipation)
            .on(QUserParticipation.userParticipation.ceremonyId.eq(QCeremony.ceremony.id));
        query = applyConditionsFromRequest(query, ceremonySearch.getRequest());
        return query
            .fetchCount();
    }

    public List<Ceremony> findTop2Ceremonies() {
        SQLQuery<Ceremony> query = transactionManager
            .selectQuery()
            .select(QCeremony.ceremony)
            .from(QCeremony.ceremony)
            .leftJoin(QUserParticipation.userParticipation)
            .on(QUserParticipation.userParticipation.ceremonyId.eq(QCeremony.ceremony.id));
        return query
            .groupBy(QCeremony.ceremony)
            .orderBy(QCeremony.ceremony.isHighlighted.desc(), QUserParticipation.userParticipation.count().desc())
            .limit(2)
            .fetch();
    }
}
