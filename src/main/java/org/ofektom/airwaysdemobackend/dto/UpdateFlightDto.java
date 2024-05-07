package org.ofektom.airwaysdemobackend.dto;

import lombok.Data;
import lombok.Getter;
import org.ofektom.airwaysdemobackend.enums.FlightDirection;
import org.ofektom.airwaysdemobackend.enums.FlightStatus;
import org.ofektom.airwaysdemobackend.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Data
public class UpdateFlightDto {
    private long duration;
    private FlightDirection flightDirection;
    private String airlineName;
    private LocalDate returnDate;
    private String flightNo;
    private Airline airline;
    private FlightStatus flightStatus;
    private Integer totalSeat;
    private Airport arrivalPort;
    private Airport departurePort;
    private List<Classes> classes;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private String arrivalPortName;
    private String departurePortName;
    private User user;
    private List<Passenger> passengers;



}
