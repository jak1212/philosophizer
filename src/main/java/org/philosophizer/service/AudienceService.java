package org.philosophizer.service;

import org.philosophizer.data.Audience;
import org.philosophizer.repositories.AudienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AudienceService {
    @Autowired
    private AudienceRepository audienceRepository;

    public Audience saveAudience(Audience audience){
        audienceRepository.save(audience);
        return audience;
    }
}
