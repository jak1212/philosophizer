package org.philosophizer.cronJobs;

import jakarta.annotation.PostConstruct;
import org.philosophizer.service.PhilosophizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("job")
public class DailyMailJobRunner implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(DailyMailJobRunner.class);

    private final PhilosophizerService philosophizerService;
    private final ApplicationContext applicationContext;

    public DailyMailJobRunner(PhilosophizerService philosophizerService, ApplicationContext applicationContext) {
        this.philosophizerService = philosophizerService;
        this.applicationContext = applicationContext;
    }


    public void run(String... args) {
        log.info("Starting daily philosophy mail job");
        int exitCode = 0;
        try {
            philosophizerService.executeMailService();
            log.info("Daily philosophy mail job completed successfully");
        } catch (Exception e) {
            log.error("Daily philosophy mail job failed", e);
            exitCode = 1;
            throw e; // IMPORTANT: fail the pod
        }
        finally {
            int finalExitCode = exitCode;
            System.exit(SpringApplication.exit(applicationContext, () -> finalExitCode));
        }
    }
}