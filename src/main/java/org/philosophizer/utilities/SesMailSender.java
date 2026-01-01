package org.philosophizer.utilities;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.philosophizer.configuration.MailProperties;
import org.philosophizer.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"prod","uat"})
public class SesMailSender implements MailSender{
    private final Session session;
    private MailProperties mailProperties;

    private static final Logger log = LoggerFactory.getLogger(SesMailSender.class);

    public SesMailSender(Session session, MailProperties mailProperties){

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
            log.info("Real message with subject " + subject + "sent to " + to);
        } catch (AddressException e) {
            log.error("MailService Message address exception to email = {} " + e.toString(), to);
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            log.error("MailService messaging exception subject={}/,/ email={}/,/ message={}" + e.toString(), subject, to, body);
            throw new RuntimeException(e);
        }
    }
}
