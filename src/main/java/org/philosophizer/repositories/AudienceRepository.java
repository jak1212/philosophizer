package org.philosophizer.repositories;

import org.philosophizer.data.Audience;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AudienceRepository extends JpaRepository<Audience, Long> {

    public List<Audience> findByIsActiveTrue();
}
