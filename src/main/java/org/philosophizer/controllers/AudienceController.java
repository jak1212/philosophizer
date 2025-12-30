package org.philosophizer.controllers;


import org.philosophizer.data.Audience;
import org.philosophizer.service.AudienceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audience")
public class AudienceController {

    @Autowired
    private AudienceService audienceService;

    private static final Logger logger = LoggerFactory.getLogger(AudienceController.class);

    @PostMapping
    public ResponseEntity<?> createAudience(@RequestBody Audience request) {
        try {
            audienceService.saveAudience(request); //what validation is taking place here?
            logger.info("ok user created through signup controller firstName={} lastName={} email = {}", request.getFirstName(), request.getLastName(), request.getEmail());
            return ResponseEntity.ok().build();
        } catch(Exception e){
            logger.error("Error creating audience firstName={} lastName={} email={}", request.getFirstName(), request.getLastName(), request.getEmail());
            return ResponseEntity.badRequest().build();
        }
    }

}
