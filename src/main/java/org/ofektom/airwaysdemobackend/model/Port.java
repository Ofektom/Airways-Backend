package org.ofektom.airwaysdemobackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ofektom.airwaysdemobackend.enums.Type;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Port {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String portAbbr;
    @OneToMany(mappedBy = "port")
    private List<Flight> flights;
    // Getters and setters
}
