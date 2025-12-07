package org.philosophizer.philosophizerTests;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.philosophizer.PhilosophizerApp;
import org.philosophizer.data.Philosophy;
import org.philosophizer.service.PhilosophizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest(classes = PhilosophizerApp.class)
@ActiveProfiles("local")
public class PhilosophizerServiceTests {

    @Autowired
    public PhilosophizerService philosophizerService;

//    public PhilosophizerServiceTests(PhilosophizerService philosophizerService){
//        this.philosophizerService = philosophizerService;
//    }

    @Test
    public void getPhilosophies(){
        List<Philosophy> philosophyList = philosophizerService.buildPhilosophiesList();

        int testValue = ThreadLocalRandom.current().nextInt(philosophyList.size() - 1);
        System.out.println(philosophyList.get(testValue).getQuote());
        System.out.println(philosophyList.get(testValue).getSaidBy());
        assertNotNull(philosophyList.get(testValue));
        assertNotNull(philosophyList.get(testValue).getQuote());
        assertNotNull(philosophyList.get(testValue).getSaidBy());


    }

}
