package org.ofektom.airwaysdemobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ofektom.airwaysdemobackend.enums.FlightDirection;
import org.ofektom.airwaysdemobackend.model.Airport;
import org.ofektom.airwaysdemobackend.model.Classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightSearchDto {
    private Long id;
    private FlightDirection flightDirection;
    private String flightNo;
    private String airline;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private int duration;
    private Airport arrivalPort;
    private LocalDate returnDate;
    private LocalTime returnTime;
    private Airport departurePort;
    private List<Classes> classes;
    private int totalSeat;
    private int availableSeat;
    private int noOfChildren;
    private int noOfAdult;
    private int noOfInfant;
}
