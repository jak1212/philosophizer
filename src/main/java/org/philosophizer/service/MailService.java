package org.philosophizer.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.philosophizer.configuration.MailProperties;
import org.philosophizer.data.Audience;
import org.philosophizer.utilities.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
