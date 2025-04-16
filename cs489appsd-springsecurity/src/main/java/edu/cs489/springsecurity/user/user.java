package edu.cs489.springsecurity.user;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
@Table(name = "users")
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
}
