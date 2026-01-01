package org.philosophizer.configuration;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MailConfigMetrics {

    private final Environment env;
    private final AtomicInteger mailEnabled = new AtomicInteger(0);

    public MailConfigMetrics(Environment env, MeterRegistry registry) {
        this.env = env;

        Gauge.builder("philosophizer_mail_enabled", mailEnabled, AtomicInteger::get)
                .description("Whether mail sending is enabled")
                .register(registry);
    }

    @PostConstruct
    void init() {
        boolean enabled = env.getProperty(
                "mail.send.enabled", Boolean.class, false);
        mailEnabled.set(enabled ? 1 : 0);
    }

    public AtomicInteger getMailEnabled() {
        return mailEnabled;
    }
}

