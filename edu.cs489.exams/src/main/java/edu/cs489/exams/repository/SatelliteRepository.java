package edu.cs489.exams.repository;

import edu.cs489.exams.entity.Satellite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SatelliteRepository extends JpaRepository<Satellite, Long> {
    // Custom query methods can be defined here if needed
}
