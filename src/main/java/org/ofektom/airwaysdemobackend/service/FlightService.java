package org.ofektom.airwaysdemobackend.service;

import org.ofektom.airwaysdemobackend.dto.FlightSearchDto;
import org.ofektom.airwaysdemobackend.model.Airport;
import org.ofektom.airwaysdemobackend.model.Flight;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    String deleteFlight(Long Id);
    List<FlightSearchDto> searchAvailableFlight(Airport departurePort, Airport arrivalPort, LocalDate departureDate, LocalDate returnDate, int noOfAdult, int noOfChildren, int noOfInfant);

    Page<Flight> getAllFlights(int pageNo, int pageSize);
    int getTotalNumberOfFlights();

}
