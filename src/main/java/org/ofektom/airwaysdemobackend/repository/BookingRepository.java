package org.ofektom.airwaysdemobackend.repository;


import org.ofektom.airwaysdemobackend.enums.BookingStatus;
import org.ofektom.airwaysdemobackend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookingStatusNotAndCreatedAtBefore(BookingStatus bookingStatus, LocalDateTime created );
    Optional<Booking> findByBookingReferenceCode(String bookingReferenceCode);

}
