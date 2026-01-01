package org.philosophizer.utilities;

public interface MailSender {
    public void send(String to, String subject, String body);
}

