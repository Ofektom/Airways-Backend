package org.ofektom.airwaysdemobackend.repository;


import org.ofektom.airwaysdemobackend.model.Booking;
import org.ofektom.airwaysdemobackend.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findAllByPassengerEmail(String passengerMail);

    Optional<List<Passenger>> findPassengerByBookingsAndPassengerEmail(Booking id, String email);
}
