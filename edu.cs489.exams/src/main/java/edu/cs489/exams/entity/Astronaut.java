package edu.cs489.exams.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "astronauts")
public class Astronaut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 20)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 20)
    private String lastName;

    @Min(0)
    @Max(50)
    private int experienceYears;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "astronaut_satellite",
            joinColumns = @JoinColumn(name = "astronaut_id"),
            inverseJoinColumns = @JoinColumn(name = "satellite_id")
    )
    private List<Satellite> satellites = new ArrayList<>();
}
