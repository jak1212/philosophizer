package org.philosophizer.utilities;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

@Component
@Profile({"prod","uat"})
public class SesMailSender implements MailSender{
    //private final Session session;
    //private MailProperties mailProperties;
    private final JavaMailSender mailSender;

    private static final Logger log = LoggerFactory.getLogger(SesMailSender.class);

    public SesMailSender(JavaMailSender mailSender){

        this.mailSender = mailSender;
    }

    @Override
    public void send(String to, String subject, String body) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(body);
            mailSender.send(msg);
            log.info("Real message with subject " + subject + "sent to " + to);
        } catch (MailException e) {
            log.error("MailService Message exception to email = {} " + e.toString(), to);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Unknown MailService exception subject={}/,/ email={}/,/ message={}" + e.toString(), subject, to, body);
            throw new RuntimeException(e);
        }
    }
}
