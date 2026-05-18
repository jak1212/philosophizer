package org.philosophizer.service;

import org.apache.cxf.common.util.StringUtils;
import org.philosophizer.data.Audience;
import org.philosophizer.data.Philosophy;
import org.philosophizer.repositories.AudienceRepository;
import org.philosophizer.repositories.PhilosophyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PhilosophizerService {


    @Autowired
    @Lazy
    private MailService mailService;
    @Autowired
    private PhilosophyRepository philosophyRepository;
    @Autowired
    private AudienceRepository audienceRepository;

    public List<Philosophy> philosophiesList;
    private Logger log = LoggerFactory.getLogger(PhilosophizerService.class);

    public void sendPhilosophy(List<Audience> audienceList){
        try {
            this.philosophiesList = buildPhilosophiesList();
            int index = ThreadLocalRandom.current().nextInt(philosophiesList.size() - 1);
            String subject = "Daily Philosophy";
            for (Audience a : audienceList) {
                String to = a.getEmail();
                mailService.sendEmail(a,
                        philosophiesList.get(index).getQuote() + "/n" +
                                philosophiesList.get(index).getSaidBy(), subject);
                log.info("Philosophizer service mail sent to recipient={}", a.getEmail());
            }
            log.info("Philosophizer service sent a batch");
        }catch(Exception e){
            log.error("Exception in sending philosophy", e);
            throw new RuntimeException("sendPhilosophy failed", e);
        }
    }

    public List<Philosophy> buildPhilosophiesList(){
        List<Philosophy> philosophiesList = philosophyRepository.findAll();

        return philosophiesList;
    }

    public void executeMailService(){
        List<Audience> audienceList = audienceRepository.findByIsActiveTrue();

        sendPhilosophy(audienceList);


    }

    public List<Philosophy> getPhilosophiesList() {
        return philosophiesList;
    }

    public void setPhilosophiesList(List<Philosophy> philosophiesList) {
        this.philosophiesList = philosophiesList;
    }
}
