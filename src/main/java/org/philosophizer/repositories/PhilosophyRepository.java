package org.philosophizer.repositories;

import org.philosophizer.data.Philosophy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhilosophyRepository extends JpaRepository<Philosophy, Long> {
}
