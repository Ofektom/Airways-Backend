package org.ofektom.airwaysdemobackend.repository;


import org.ofektom.airwaysdemobackend.model.Booking;
import org.ofektom.airwaysdemobackend.model.BookingConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingConfirmationTokenRepository extends JpaRepository<BookingConfirmationToken, Long> {
    BookingConfirmationToken findByToken(String token);

    BookingConfirmationToken findByBooking(Booking booking);
}
