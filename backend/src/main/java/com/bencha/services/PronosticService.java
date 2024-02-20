package com.bencha.services;

import com.bencha.db.dao.PronosticDao;
import com.bencha.db.dao.UserParticipationDao;
import com.bencha.db.generated.*;
import com.bencha.enums.AwardsType;
import com.bencha.enums.PronosticType;
import com.bencha.services.configuration.ConfigurationService;
import com.bencha.webservices.beans.*;
import com.querydsl.core.Tuple;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class PronosticService {
    private final PronosticDao pronosticDao;
    private final UserParticipationDao userParticipationDao;
    private final ConfigurationService configurationService;
    private final AwardNomineeService awardNomineeService;

    @Inject
    public PronosticService(
        UserParticipationDao userParticipationDao,
        PronosticDao pronosticDao,
        ConfigurationService configurationService,
        AwardNomineeService awardNomineeService
    ) {
        this.userParticipationDao = userParticipationDao;
        this.pronosticDao = pronosticDao;
        this.configurationService = configurationService;
        this.awardNomineeService = awardNomineeService;
    }

    public CeremonyPronostics findUserPronostics(Long userId, Long ceremonyId) {
        UserParticipation userParticipation = userParticipationDao.findUserParticipation(userId, ceremonyId);
        if (userParticipation == null) {
            return CeremonyPronostics.of(
                Map.of(),
                Map.of()
            );
        }
        List<Pronostic> pronostics = this.pronosticDao.findUserPronosticsForParticipation(userParticipation.getId());
        Map<Boolean, List<Pronostic>> pronosticsPartitioned = pronostics.stream()
            .collect(Collectors.partitioningBy(
                pronostic -> {
                    PronosticType pronosticType = PronosticType.valueOf(pronostic.getType());
                    return PronosticType.WINNER.equals(pronosticType);
                }
            )
        );
        return CeremonyPronostics
            .of(
                pronosticsPartitioned.get(false)
                    .stream()
                    .collect(Collectors.toMap(
                        Pronostic::getAwardId,
                        awardNomineeService::pronosticToPronosticChoice
                    )
                ),
                pronosticsPartitioned.get(true)
                    .stream()
                    .collect(Collectors.toMap(
                            Pronostic::getAwardId,
                            awardNomineeService::pronosticToPronosticChoice
                        )
                    )
            );
    }

    public void saveUserChoice(Long userId, Long ceremonyId, PronosticChoice pronosticChoice) {
        Long userParticipationId = findUserParticipationOrCreateIt(userId, ceremonyId);
        Pronostic pronostic = pronosticDao.findUserPronosticsForAwardAndPronosticType(userParticipationId, pronosticChoice.getAwardId(), pronosticChoice.getType());
        if (pronostic == null) {
            pronostic = new Pronostic();
        }
        pronostic.setNomineeId(pronosticChoice.getNomineeId());
        pronostic.setAwardId(pronosticChoice.getAwardId());
        pronostic.setUserParticipationId(userParticipationId);
        pronostic.setType(pronosticChoice.getType().name());
        if (pronosticChoice.getNominee() != null) {
            pronostic.setTdmbMovieId(pronosticChoice.getNominee().getTmdbMovieId().longValue());
            pronostic.setTdmbPersonId(pronosticChoice.getNominee().getTmdbPersonId().longValue());
        }
        pronosticDao.save(pronostic);
    }

    public void removeCeremonyPronostics(Long userId, Long ceremonyId, AwardDeletion awardDeletion) {
        Long userParticipationId = findUserParticipationOrCreateIt(userId, ceremonyId);
        List<Pronostic> pronostics = pronosticDao.findUserPronosticsForAward(userParticipationId, awardDeletion.getAwardId());
        pronostics
            .forEach(pronostic -> pronosticDao.delete(pronostic.getId()));
    }

    public void removeAnonymousCeremonyPronostics(AwardDeletion awardDeletion) {
        List<Pronostic> pronostics = pronosticDao.findUserPronosticsForAward(awardDeletion.getParticipationId(), awardDeletion.getAwardId());
        pronostics
            .forEach(pronostic -> pronosticDao.delete(pronostic.getId()));
    }

    public void saveAnonymousChoice(PronosticChoice pronosticChoice) {
        Pronostic pronostic = pronosticDao.findUserPronosticsForAwardAndPronosticType(pronosticChoice.getParticipationId(), pronosticChoice.getAwardId(), pronosticChoice.getType());
        if (pronostic == null) {
            pronostic = new Pronostic();
        }
        pronostic.setNomineeId(pronosticChoice.getNomineeId());
        pronostic.setAwardId(pronosticChoice.getAwardId());
        pronostic.setUserParticipationId(pronosticChoice.getParticipationId());
        pronostic.setType(pronosticChoice.getType().name());
        if (pronosticChoice.getNominee() != null) {
            pronostic.setTdmbMovieId(pronosticChoice.getNominee().getTmdbMovieId().longValue());
            if (pronosticChoice.getNominee().getTmdbPersonId() != null) {
                pronostic.setTdmbPersonId(pronosticChoice.getNominee().getTmdbPersonId().longValue());
            }
        }
        pronosticDao.save(pronostic);
    }

    public Long findUserParticipationOrCreateIt(Long userId, Long ceremonyId) {
        UserParticipation userParticipation = userParticipationDao.findUserParticipation(userId, ceremonyId);
        if (userParticipation == null) {
            userParticipation = new UserParticipation();
            userParticipation.setUserId(userId);
            userParticipation.setCeremonyId(ceremonyId);
            userParticipation.setShareCode(UUID.randomUUID().toString());
            return userParticipationDao.save(userParticipation).getId();
        }
        return userParticipation.getId();
    }

    public Long createAnonymousParticipation(Long ceremonyId, CeremonyPronostics ceremonyPronostics) {
        UserParticipation userParticipation = new UserParticipation();
        userParticipation.setCeremonyId(ceremonyId);
        userParticipation.setShareCode(UUID.randomUUID().toString());
        userParticipation = userParticipationDao.save(userParticipation);
        Long userParticipationId = userParticipation.getId();
        ceremonyPronostics
            .getWinnerPicks()
            .values()
            .forEach(pronosticChoice -> {
                pronosticChoice.setParticipationId(userParticipationId);
                saveAnonymousChoice(pronosticChoice);
            });
        ceremonyPronostics
            .getFavoritesPicks()
            .values()
            .forEach(pronosticChoice -> {
                pronosticChoice.setParticipationId(userParticipationId);
                saveAnonymousChoice(pronosticChoice);
            });
        return userParticipationId;
    }

    public void linkPronosticsToUser(Long ceremonyId, Long userId, CeremonyPronostics ceremonyPronostics) {
        UserParticipation userParticipation = userParticipationDao.findUserParticipation(userId, ceremonyId);
        if (userParticipation == null) {
            userParticipation = new UserParticipation();
            userParticipation.setCeremonyId(ceremonyId);
            userParticipation.setUserId(userId);
            userParticipation.setShareCode(UUID.randomUUID().toString());
            userParticipation = userParticipationDao.save(userParticipation);
            Long userParticipationId = userParticipation.getId();
            ceremonyPronostics
                .getFavoritesPicks()
                .values()
                .forEach(pronosticChoice -> {
                    pronosticChoice.setParticipationId(userParticipationId);
                    saveAnonymousChoice(pronosticChoice);
                });
            ceremonyPronostics
                .getWinnerPicks()
                .values()
                .forEach(pronosticChoice -> {
                    pronosticChoice.setParticipationId(userParticipationId);
                    saveAnonymousChoice(pronosticChoice);
                });
        }
    }

    public String getPronosticsShareLink(Long userId, Long ceremonyId) {
        return configurationService.getShareUrl() + userParticipationDao.getShareCodeByUserIdCeremonyId(userId, ceremonyId);
    }

    public String getPronosticsShareLink(Long participationId) {
        return configurationService.getShareUrl() + userParticipationDao.getShareCodeByParticipationId(participationId);
    }

    public List<AwardShare> findAwardsShareWithParticipationId(Long participationId) {
        List<Tuple> tuples = pronosticDao.findUserPronosticsWithNomineeAndAwardForParticipation(participationId);
        return tuples
            .stream()
            .map(tuple -> {
                Pronostic pronostic = tuple.get(QPronostic.pronostic);
                AwardNominee awardNominee = tuple.get(QAwardNominee.awardNominee);
                Award award = tuple.get(QAward.award);
                if (pronostic != null && award != null) {
                    AwardsType awardsType = AwardsType.valueOf(award.getType());
                    Nominee nominee = awardNomineeService.pronosticToNominee(
                        pronostic,
                        awardNominee,
                        awardsType
                    );
                    PronosticType pronosticType = PronosticType.valueOf(pronostic.getType());
                    return AwardShare.of(
                        award.getName(),
                        awardsType,
                        pronosticType,
                        nominee
                    );
                } return null;
            })
            .filter(Objects::nonNull)
            .toList();
    }
}
