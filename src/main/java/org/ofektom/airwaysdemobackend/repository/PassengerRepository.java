package org.ofektom.airwaysdemobackend.repository;

import org.ofektom.airwaysdemobackend.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
