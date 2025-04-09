package edu.cs489app.library.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "authors")
@NoArgsConstructor
@Data
public class Authors {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "author_id")
    private Long id;
    private String firstName;
    private String lastName;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "books")
    private List<Book> books;

    public Authors(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
