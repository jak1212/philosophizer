package org.philosophizer.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!job")
public class JobExecutionGuard {

    @PostConstruct
    void guard() {
        if (System.getenv("APP_MODE") != null &&
                System.getenv("APP_MODE").equals("job")) {
            throw new IllegalStateException(
                    "APP_MODE=job but job profile not active");
        }
    }
}

