package org.philosophizer.mailTests;


import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.philosophizer.PhilosophizerApp;
import org.philosophizer.data.Audience;
import org.philosophizer.data.Philosophy;
import org.philosophizer.service.MailService;
import org.philosophizer.service.PhilosophizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PhilosophizerApp.class)
//@ActiveProfiles("local")
public class MailTest {

    @Autowired
    public PhilosophizerService philosophizerService;

    private final RestTemplate restTemplate = new RestTemplate();




    @Test
    public void  sendTestEmail(){

        List<Audience> aList = new ArrayList<>();
        aList.add(new Audience("Jack", "Kelly", "jk0827@gmail.com"));

        philosophizerService.sendPhilosophy(aList);
        //go check if email generated and looks correct
        List<Philosophy> philosophies = philosophizerService.getPhilosophiesList();


        assertNotNull(philosophies);
        assertNotNull(philosophies.get(0).getSaidBy());
        assertNotNull(philosophies.get(0).getQuote());



//        String url = "http://localhost:8025/api/v2/messages";
//        ResponseEntity<String> response =
//                restTemplate.getForEntity(url, String.class);
//
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//
//
//        String body = response.getBody();
//
//        assertTrue(body.contains("test@local.dev"));
//        assertTrue(body.contains("Daily Philosophy"));
    }
}
