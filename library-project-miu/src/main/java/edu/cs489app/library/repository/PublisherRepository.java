package edu.cs489app.library.repository;

import edu.cs489app.library.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByNameIgnoreCase(String name);
    void deleteByName(String name);
}
