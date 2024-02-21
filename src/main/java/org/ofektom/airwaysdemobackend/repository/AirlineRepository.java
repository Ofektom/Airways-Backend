package org.ofektom.airwaysdemobackend.repository;


import org.ofektom.airwaysdemobackend.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, String> {
}
