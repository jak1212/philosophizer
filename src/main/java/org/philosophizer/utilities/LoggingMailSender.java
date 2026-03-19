package org.philosophizer.utilities;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"local"})
@Component
class LoggingMailSender implements MailSender {
    public void send(String to, String subject, String body) {
        System.out.println("EMAIL -> " + to + " | " + subject);
    }
}