package edu.cs489.exams.repository;

import edu.cs489.exams.entity.Astronaut;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AstronautRepository extends JpaRepository<Astronaut, Long> {
    // Custom query methods can be defined here if needed
}
