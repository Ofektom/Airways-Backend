package org.ofektom.airwaysdemobackend.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ofektom.airwaysdemobackend.enums.BookingStatus;
import org.ofektom.airwaysdemobackend.enums.FlightDirection;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingEditingDto {
    private FlightDirection tripType;
    private List<PassengerDTo> passengers;
    private List<BookingFlightDto> bookingFlights;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
}
