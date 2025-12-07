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

@Service
public class MailService {
    public Session session;
    private MailProperties mailProperties;

    private static final Logger log = LoggerFactory.getLogger(MailService.class);



    public MailService(Session session, MailProperties mailProperties){

        this.session = session;
        this.mailProperties = mailProperties;
    }

    public void sendTestEmail(String to){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailProperties.getFrom())); //can I set this to be dynamic to env variables? DONE
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Hey buddy");
            message.setText("You're doing great, just keep going. Like its a walk in the park and youre observing a new thing. This is fun!");
            Transport.send(message);
            log.info("Test email sent to " + to);


        } catch (AddressException e) {
            log.error("Failed to send test email to {}", to, e);
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            log.error("Failed to send test email to {}", to, e);
            throw new RuntimeException(e);
        }
    }

    public void sendEmail(String to, String messageText, String subject){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailProperties.getFrom()));
            message.setSubject(subject);
            message.setText(messageText);
            Transport.send(message);
            log.info("Real message with subject " + subject + "sent to " + to);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
