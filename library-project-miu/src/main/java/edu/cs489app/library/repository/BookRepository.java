package edu.cs489app.library.repository;

import edu.cs489app.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    void deleteByIsbn(String isbn);
    List<Book> findByTitle(String title);
}

