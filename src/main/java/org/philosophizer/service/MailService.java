package org.philosophizer.service;

import org.philosophizer.data.Audience;
import org.philosophizer.utilities.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {
    private static final Logger log = LoggerFactory.getLogger(MailService.class);
    private final MailSender mailSender;


    public MailService(MailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void sendEmail(List<Audience> audienceList, String body, String subject) {
        for (Audience a : audienceList) {
            mailSender.send(a.getEmail(), subject, body);
        }
    }

    public void sendEmail(Audience a, String body, String subject) {
        {
            mailSender.send(a.getEmail(), subject, body);
        }
    }
}
