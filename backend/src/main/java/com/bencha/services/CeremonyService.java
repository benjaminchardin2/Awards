package com.bencha.services;

import com.bencha.db.dao.CeremonyDao;
import com.bencha.db.generated.Ceremony;
import com.bencha.webservices.beans.CeremonyRequest;
import com.bencha.webservices.beans.Metadata;
import com.bencha.webservices.beans.PaginatedRequest;
import com.bencha.webservices.beans.PaginatedResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Singleton
public class CeremonyService {
    private final CeremonyDao ceremonyDao;
    private final AwardService awardService;

    @Inject
    public CeremonyService(CeremonyDao ceremonyDao, AwardService awardService) {
        this.ceremonyDao = ceremonyDao;
        this.awardService = awardService;
    }

    public PaginatedResponse<Ceremony> searchCeremonies(PaginatedRequest<CeremonyRequest> ceremonyRequest) {
        List<Ceremony> ceremonies = ceremonyDao.searchCeremonies(ceremonyRequest);
        Long count = ceremonyDao.countCeremonies(ceremonyRequest);
        Metadata metadata = Metadata.of(
            ceremonyRequest.getOffset(),
            ceremonyRequest.getPageSize(),
            ceremonyRequest.getOffset() - ceremonyRequest.getPageSize(),
            ceremonyRequest.getOffset() + ceremonies.size(),
            new BigDecimal(ceremonyRequest.getOffset()).divide(new BigDecimal(ceremonyRequest.getPageSize()), RoundingMode.UP).scale(),
            new BigDecimal(count).divide(new BigDecimal(ceremonyRequest.getPageSize()), RoundingMode.UP).scale(),
            count.intValue()
        );
        return PaginatedResponse.of(
            metadata,
            ceremonies
        );
    }

    public List<Ceremony> findTop2Ceremonies() {
        return ceremonyDao.findTop2Ceremonies();
    }

    public void loadCacheForHighlightedCeremonies() {
        ceremonyDao.findHighlightedCeremonies()
            .forEach(awardService::loadCacheForCeremony);
    }
}
