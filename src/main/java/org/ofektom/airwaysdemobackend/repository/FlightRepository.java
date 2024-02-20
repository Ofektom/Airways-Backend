package org.ofektom.airwaysdemobackend.repository;

import org.ofektom.airwaysdemobackend.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
