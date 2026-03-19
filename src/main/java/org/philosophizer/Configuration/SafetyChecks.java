package org.philosophizer.configuration;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;
import java.util.Arrays;


@Component
@Profile({"prod","uat"})
public class SafetyChecks {

    private final MailConfigMetrics mailConfigMetrics;
    private final Logger log = LoggerFactory.getLogger(SafetyChecks.class);

    @Autowired
    private Environment env;

    public SafetyChecks(MailConfigMetrics mailConfigMetrics){
        this.mailConfigMetrics = mailConfigMetrics;
    }

    @PostConstruct
    void validateAll() {
        checkProfiles();
        checkEnvLabel();
        checkHibernateDDL();
        mailConfigCheck();
        requireExplicitSendFlag();
        schedulerGuard();
        checkMetrics();
        logRuntimeContext();
    }

    void validate() {
        if (env.acceptsProfiles(Profiles.of("prod")) &&
                System.getenv("SMTP_HOST").contains("localhost")) {
            throw new IllegalStateException("Mailhog in prod");
        }
    }


    void checkProfiles() {
        if (env.getActiveProfiles().length != 1) {
            throw new IllegalStateException(
                    "Exactly one Spring profile must be active, found: " +
                            Arrays.toString(env.getActiveProfiles()));
        }
    }


    void checkEnvLabel() {
        String appEnv = env.getProperty("app.env");
        String active = env.getActiveProfiles()[0];
        System.out.println("App env " + appEnv);
        System.out.println("active profile " + active);

        if (!active.equals(appEnv)) {
            throw new IllegalStateException(
                    "Profile and app.env mismatch: " + active + " vs " + appEnv);
        }
    }


    void checkHibernateDDL() {
        if (env.acceptsProfiles(Profiles.of("prod"))) {
            String ddl = env.getProperty("spring.jpa.hibernate.ddl-auto");
            if (!"none".equals(ddl) && !"validate".equals(ddl)) {
                throw new IllegalStateException(
                        "DDL auto must be none or validate in prod, found: " + ddl);
            }
        }
    }


    void mailConfigCheck() {
        String host = env.getProperty("mail.smtp.host", "");

        if (env.acceptsProfiles(Profiles.of("prod")) && host.contains("localhost")) {
            throw new IllegalStateException("Mailhog configured in prod");
        }

        if (env.acceptsProfiles(Profiles.of("local")) && !host.contains("localhost")) {
            throw new IllegalStateException("Non-local SMTP in local profile");
        }
    }

    void requireExplicitSendFlag() {
        boolean enabled = env.getProperty("mail.send.enabled", Boolean.class, false);

        if (env.acceptsProfiles(Profiles.of("prod")) && !enabled) {
            throw new IllegalStateException("mail.send.enabled=false in prod");
        }
    }


    void schedulerGuard() {
        boolean enabled = env.getProperty("jobs.daily-email.enabled",
                Boolean.class, false);

        if (!enabled && env.acceptsProfiles(Profiles.of("prod"))) {
            throw new IllegalStateException("Daily email job disabled in prod");
        }
    }


    void logRuntimeContext() {
        log.info("Pod: {}, Namespace: {}",
                System.getenv("HOSTNAME"),
                System.getenv("KUBERNETES_NAMESPACE"));
    }


    void checkMetrics() {
        boolean managementEndpointAvailable = mailConfigMetrics.getMailEnabled().get() == 1;
        if (!managementEndpointAvailable) {
            log.warn("Prometheus endpoint not exposed");
        }
    }





}
