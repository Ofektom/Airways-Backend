package org.ofektom.airwaysdemobackend.service;


import org.ofektom.airwaysdemobackend.dto.AddFlightDto;
import org.ofektom.airwaysdemobackend.dto.FlightSearchDto;
import org.ofektom.airwaysdemobackend.dto.FlightSearchResponse;
import org.ofektom.airwaysdemobackend.enums.FlightDirection;
import org.ofektom.airwaysdemobackend.model.Airport;
import org.ofektom.airwaysdemobackend.model.Flight;
import org.ofektom.airwaysdemobackend.model.Seat;
import org.ofektom.airwaysdemobackend.model.SeatList;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface FlightService {
    String deleteFlight(Long Id);
    Map<String, FlightSearchResponse> searchAvailableFlight(Airport departurePort, Airport arrivalPort, LocalDate departureDate, FlightDirection flightDirection, LocalDate returnDate) ;
    public FlightSearchResponse getReturningFlights(Airport departurePort, Airport arrivalPort, LocalDate returnDate);
    public List<FlightSearchDto> getAllReturningFlights(Airport departurePort, Airport arrivalPort);
    List<FlightSearchDto> getAllDepartingFlights(Airport departurePort, Airport arrivalPort);
      FlightSearchResponse getDepartingFlights(Airport departurePort, Airport arrivalPort, LocalDate departureDate);
    Page<Flight> getAllFlights(int pageNo, int pageSize);
    public int getTotalNumberOfFlights();
    String addNewFlight(AddFlightDto flightDto);
    LocalDate calculateArrivalDate(LocalDate departureDate, long durationMinutes);
    String generateRandomNumber(int length);
    String generateRandomLetters(int length);
    List<SeatList> generateSeatList (Seat seat);
    String confirmFlight(Long Id);

    FlightSearchDto getFlightDetails(Long flightId);
}
