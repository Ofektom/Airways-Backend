package org.ofektom.airwaysdemobackend.repository;


import org.ofektom.airwaysdemobackend.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirlineRepository extends JpaRepository<Airline, String> {
    Optional<Airline> findByNameIgnoreCase(String s);
}
