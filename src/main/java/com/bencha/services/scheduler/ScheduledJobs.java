package com.bencha.services.scheduler;

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

    @Inject
    public ScheduledJobs(Scheduler scheduler, VerificationService verificationService) {
        this.scheduler = scheduler;
        this.verificationService = verificationService;
    }

    public void scheduleJobs() {
        scheduler.schedule(
            "Delete expired verification token",
            verificationService::deleteOutdatedVerificationTokens,
            Schedules.fixedDelaySchedule(Duration.ofMinutes(30))
        );
    }

}
