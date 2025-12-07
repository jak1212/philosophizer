package org.philosophizer.service;

import org.apache.cxf.common.util.StringUtils;
import org.philosophizer.data.Audience;
import org.philosophizer.data.Philosophy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public List<Philosophy> philosophiesList;
    private MailService mailService;
    private Logger log = LoggerFactory.getLogger(PhilosophizerService.class);

    public void sendPhilosophy(List<Audience> audienceList){
        this.philosophiesList = buildPhilosophiesList();
        int index = ThreadLocalRandom.current().nextInt(philosophiesList.size() - 1);
        String subject = "Daily Philosophy";
        for(Audience a : audienceList){
            String to = a.getEmail();
            mailService.sendEmail(to,
                    philosophiesList.get(index).getQuote() + "/n" +
                    philosophiesList.get(index).getSaidBy(), subject);
        }
        log.info("Philosophizer service sent a batch");
    }

    public List<Philosophy> buildPhilosophiesList(){
        List<Philosophy> philosophiesList = new ArrayList<>();
        String filePath = "src\\main\\resources\\philosophies.csv";

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;

            while((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if(parts.length < 2){ continue;}

                String quote = parts[0].replace("\"", "").trim();
                String saidBy = StringUtils.capitalize(parts[1].trim());

                philosophiesList.add(new Philosophy(quote, saidBy));
            }

        }catch(IOException e){e.printStackTrace();}

        return philosophiesList;
    }
}
