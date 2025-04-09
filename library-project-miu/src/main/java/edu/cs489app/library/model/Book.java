package edu.cs489app.library.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(unique = true, nullable = false)
    private String isbn;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "book_authors", // Joining table
            joinColumns = @JoinColumn(name = "book_id"), // Which entity owns the association
            inverseJoinColumns = @JoinColumn(name = "author_id")) // Other entity, Author's PK or any other field(unique)
    private List<Authors> authors;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Publisher publisher; // Book ->--- || - Publisher
}
