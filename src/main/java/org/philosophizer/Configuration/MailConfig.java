package org.philosophizer.Configuration;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MailConfig {
    private final MailProperties props;

    public MailConfig(MailProperties props){
        this.props = props;
    }

    @Bean
    public Session mailSession() {
        Properties p = new Properties();
        p.put("mail.smtp.auth", props.isAuth());
        p.put("mail.smtp.starttls.enable", props.isStarttlsEnable());
        p.put("mail.smtp.host", props.getHost());
        p.put("mail.smtp.port", props.getPort());

        return Session.getInstance(p, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        props.getUsername(),
                        props.getPassword()
                );
            }
        });

    }
        }


