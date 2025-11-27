package org.philosophizer.Configuration;

import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MainConfig {
    private final MailProperties props;

    public MainConfig(MailProperties props){
        this.props = props;
    }

    @Bean
    public Session mailSession() {
        Properties p = new Properties();
        p.put("mail.smtp.auth", props.isAuth());
        p.put("mail.smtp.starttls.enable", props.isStarttlsEnable());
        p.put("mail.smtp.host", props.getHost());
        p.put("mail.smtp.port", props.getPort());

        return Session.getInstance(p, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(
                        props.getUsername(),
                        props.getPassword()
                );
            }
        });
            }
        }
    }
}
