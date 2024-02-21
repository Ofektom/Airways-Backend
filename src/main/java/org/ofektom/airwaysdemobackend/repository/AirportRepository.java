package org.ofektom.airwaysdemobackend.repository;


import jakarta.transaction.Transactional;
import org.ofektom.airwaysdemobackend.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface AirportRepository extends JpaRepository<Airport, String> {

}