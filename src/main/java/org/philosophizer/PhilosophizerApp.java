package org.philosophizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = "org.philosophizer")
@EnableScheduling
@ConfigurationPropertiesScan
public class PhilosophizerApp
{
    private static final Logger log = LoggerFactory.getLogger(PhilosophizerApp.class);
    

    public static void main( String[] args )
    {
        log.info("Starting Philosophizer application...");
        SpringApplication.run(PhilosophizerApp.class, args);



    }
}
