package com.bencha.services.scheduler;

import com.bencha.services.CeremonyService;
import com.bencha.services.VerificationService;
import com.coreoz.wisp.Scheduler;
import com.coreoz.wisp.schedule.Schedules;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Duration;

@Singleton
public class ScheduledJobs {

    private final Scheduler scheduler;
    private final VerificationService verificationService;
    private final CeremonyService ceremonyService;

    @Inject
    public ScheduledJobs(Scheduler scheduler, VerificationService verificationService, CeremonyService ceremonyService) {
        this.scheduler = scheduler;
        this.verificationService = verificationService;
        this.ceremonyService = ceremonyService;
    }

    public void scheduleJobs() {
        scheduler.schedule(
            "Delete expired verification token",
            verificationService::deleteOutdatedVerificationTokens,
            Schedules.fixedDelaySchedule(Duration.ofMinutes(30))
        );
        scheduler.schedule(
            "Load cache for highlighted ceremonies",
            ceremonyService::loadCacheForHighlightedCeremonies,
            Schedules.afterInitialDelay(Schedules.fixedDelaySchedule(Duration.ofHours(24)), Duration.ZERO)
        );
    }

}
