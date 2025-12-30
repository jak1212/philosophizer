package org.philosophizer.service;

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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {
    private final Session session;
    private MailProperties mailProperties;




    private static final Logger log = LoggerFactory.getLogger(MailService.class);



    public MailService(Session session, MailProperties mailProperties){

        this.session = session;
        this.mailProperties = mailProperties;
    }



    public void sendEmail(String to, String messageText, String subject){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailProperties.getFrom()));
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(to)});
            message.setSubject(subject);
            message.setText(messageText);
            Transport.send(message);
            log.info("Real message with subject " + subject + "sent to " + to);
        } catch (AddressException e) {
            log.error("MailService Message address exception to email = {} " + e.toString(), to);
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            log.error("MailService messaging exception subject={}/,/ email={}/,/ message={}" + e.toString(), subject, to, messageText);
            throw new RuntimeException(e);
        }
    }
}
