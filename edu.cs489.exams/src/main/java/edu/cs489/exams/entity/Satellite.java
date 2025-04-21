package edu.cs489.exams.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.cs489.exams.enums.OrbitType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "satellites")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Satellite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @Past
    private LocalDate launchDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrbitType orbitType;

    private boolean decommissioned;

    @ManyToMany(mappedBy = "satellites")
    @JsonIgnore
    private List<Astronaut> astronauts = new ArrayList<>();



}
