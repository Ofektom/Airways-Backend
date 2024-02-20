package org.ofektom.airwaysdemobackend.repository;

import org.ofektom.airwaysdemobackend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
