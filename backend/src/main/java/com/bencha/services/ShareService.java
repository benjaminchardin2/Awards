package com.bencha.services;

import com.bencha.db.dao.UserParticipationDao;
import com.bencha.db.generated.QCeremony;
import com.bencha.db.generated.QUserParticipation;
import com.bencha.db.generated.UserParticipation;
import com.bencha.enums.PronosticType;
import com.bencha.webservices.beans.AwardShare;
import com.bencha.webservices.beans.CeremonyShare;
import com.querydsl.core.Tuple;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ShareService {
    private final UserParticipationDao userParticipationDao;
    private final PronosticService pronosticService;

    @Inject
    public ShareService(
        UserParticipationDao userParticipationDao,
        PronosticService pronosticService
    ) {
        this.userParticipationDao = userParticipationDao;
        this.pronosticService = pronosticService;
    }

    public CeremonyShare findCeremonyShare(String shareCode) {
        Tuple tuple = userParticipationDao.findUserParticipationWithCeremonyNameByShareCode(shareCode);
        if (tuple == null) {
            return null;
        }
        UserParticipation userParticipation = tuple.get(QUserParticipation.userParticipation);
        if (userParticipation == null) {
            return null;
        }
        String ceremonyName = tuple.get(QCeremony.ceremony.name);
        List<AwardShare> awardShares = pronosticService.findAwardsShareWithParticipationId(userParticipation.getId());
        return CeremonyShare
            .of(
                ceremonyName,
                userParticipation.getCeremonyId(),
                awardShares
                    .stream()
                    .filter(awardShare -> PronosticType.WINNER.equals(awardShare.getPronosticType()))
                    .toList(),
                awardShares
                    .stream()
                    .filter(awardShare -> PronosticType.FAVORITE.equals(awardShare.getPronosticType()))
                    .toList()
            );
    }
}
