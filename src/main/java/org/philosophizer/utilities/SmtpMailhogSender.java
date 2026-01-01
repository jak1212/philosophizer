package org.philosophizer.utilities;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.philosophizer.configuration.MailProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
public class SmtpMailhogSender implements MailSender{
    private final Session session;
    private MailProperties mailProperties;
    @Value("${mailhog.api.url}")
    private String mailhogUrl;


    private static final Logger log = LoggerFactory.getLogger(SmtpMailhogSender.class);

    public SmtpMailhogSender(Session session, MailProperties mailProperties){

        this.session = session;
        this.mailProperties = mailProperties;
    }


    @Override
    public void send(String to, String subject, String body) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailProperties.getFrom()));
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(to)});
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            log.info("Local message with subject " + subject + "sent to " + to);
        } catch (AddressException e) {
            log.error("Local MailService Message address exception to email = {} " + e.toString(), to);
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            log.error("Local MailService messaging exception subject={}/,/ email={}/,/ message={}" + e.toString(), subject, to, body);
            throw new RuntimeException(e);
        }
    }
}
