package edu.cs489app.library.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private Long id;
    @Column(nullable = false)
    private String unitNo;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private Integer zip;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    private Publisher publisher;
    public Address(String unitNo, String street, String city, String state, Integer zip) {
        this.unitNo = unitNo;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
}
