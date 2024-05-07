package org.ofektom.airwaysdemobackend.repository;


import org.ofektom.airwaysdemobackend.model.BookingFlight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingFlightRepository extends JpaRepository<BookingFlight, Long> {
}
