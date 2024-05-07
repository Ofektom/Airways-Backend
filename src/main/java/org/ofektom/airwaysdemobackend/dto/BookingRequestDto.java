package org.ofektom.airwaysdemobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ofektom.airwaysdemobackend.enums.FlightDirection;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto {
    private FlightDirection tripType;
    private List<PassengerDTo> passengers;
   private List<BookingFlightDto>bookingFlights;
}
