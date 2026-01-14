package org.philosophizer.cronJobs;

import jakarta.annotation.PostConstruct;
import org.philosophizer.service.PhilosophizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("job")
public class DailyMailJobRunner {

    private static final Logger log =
            LoggerFactory.getLogger(DailyMailJobRunner.class);

    private final PhilosophizerService philosophizerService;

    public DailyMailJobRunner(PhilosophizerService philosophizerService) {
        this.philosophizerService = philosophizerService;
    }

    @PostConstruct
    public void run() {
        log.info("Starting daily philosophy mail job");

        try {
            philosophizerService.executeMailService();
            log.info("Daily philosophy mail job completed successfully");
        } catch (Exception e) {
            log.error("Daily philosophy mail job failed", e);
            throw e; // IMPORTANT: fail the pod
        }
    }
}