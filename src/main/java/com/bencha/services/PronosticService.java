package com.bencha.services;

import com.bencha.db.dao.PronosticDao;
import com.bencha.db.dao.UserParticipationDao;
import com.bencha.db.generated.Pronostic;
import com.bencha.db.generated.UserParticipation;
import com.bencha.services.tmdb.TmdbConfigurationService;
import com.bencha.services.tmdb.TmdbMovieService;
import com.bencha.webservices.beans.Nominee;
import com.bencha.webservices.beans.PronosticChoice;
import info.movito.themoviedbapi.model.MovieDb;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class PronosticService {
    private final PronosticDao pronosticDao;
    private final UserParticipationDao userParticipationDao;
    private final TmdbMovieService tmdbMovieService;
    private final TmdbConfigurationService tmdbConfigurationService;

    @Inject
    public PronosticService(
        UserParticipationDao userParticipationDao,
        PronosticDao pronosticDao,
        TmdbMovieService tmdbMovieService,
        TmdbConfigurationService tmdbConfigurationService
    ) {
        this.userParticipationDao = userParticipationDao;
        this.pronosticDao = pronosticDao;
        this.tmdbMovieService = tmdbMovieService;
        this.tmdbConfigurationService = tmdbConfigurationService;
    }

    public Map<Long, PronosticChoice> findUserPronostics(Long userId, Long ceremonyId) {
        UserParticipation userParticipation = userParticipationDao.findUserParticipation(userId, ceremonyId);
        if (userParticipation == null) {
            return Map.of();
        }
        List<Pronostic> pronostics = this.pronosticDao.findUserPronosticsForParticipation(userParticipation.getId());
        return pronostics.stream()
            .collect(Collectors.toMap(
                Pronostic::getAwardId,
                pronostic -> {
                    PronosticChoice pronosticChoice = new PronosticChoice();
                    pronosticChoice.setNomineeId(pronostic.getNomineeId());
                    pronosticChoice.setAwardId(pronostic.getAwardId());
                    if (pronostic.getNomineeId() == null) {
                        Nominee nomineeDto = new Nominee();
                        if (pronostic.getTdmbMovieId() != null) {
                            MovieDb movieDb = tmdbMovieService.getMovieById(pronostic.getTdmbMovieId());
                            nomineeDto.setNomineeId(null);
                            nomineeDto.setMovieTitle(movieDb.getOriginalTitle());
                            nomineeDto.setFrenchMovieTitle(movieDb.getTitle());
                            nomineeDto.setTmdbMovieId(movieDb.getId());
                            nomineeDto.setMovieMediaUrl(tmdbConfigurationService.buildMediaUrl(movieDb.getPosterPath()));
                        }
                        pronosticChoice.setNominee(nomineeDto);
                    }
                    return pronosticChoice;
                }
            ));
    }

    public void saveUserChoice(Long userId, Long ceremonyId, PronosticChoice pronosticChoice) {
        Long userParticipationId = findUserParticipationOrCreateIt(userId, ceremonyId);
        Pronostic pronostic = pronosticDao.findUserPronosticsForAward(userParticipationId, pronosticChoice.getAwardId());
        if (pronostic == null) {
            pronostic = new Pronostic();
        }
        pronostic.setNomineeId(pronosticChoice.getNomineeId());
        pronostic.setAwardId(pronosticChoice.getAwardId());
        pronostic.setUserParticipationId(userParticipationId);
        if (pronosticChoice.getNominee() != null) {
            pronostic.setTdmbMovieId(pronosticChoice.getNominee().getTmdbMovieId().longValue());
            pronostic.setTdmbPersonId(pronosticChoice.getNominee().getTmdbPersonId().longValue());
        }
        pronosticDao.save(pronostic);
    }

    public Long findUserParticipationOrCreateIt(Long userId, Long ceremonyId) {
        UserParticipation userParticipation = userParticipationDao.findUserParticipation(userId, ceremonyId);
        if (userParticipation == null) {
            userParticipation = new UserParticipation();
            userParticipation.setUserId(userId);
            userParticipation.setCeremonyId(ceremonyId);
            return userParticipationDao.save(userParticipation).getId();
        }
        return userParticipation.getId();
    }
}
