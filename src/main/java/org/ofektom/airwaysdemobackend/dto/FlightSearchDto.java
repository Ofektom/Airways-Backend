package org.ofektom.airwaysdemobackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ofektom.airwaysdemobackend.enums.FlightDirection;
import org.ofektom.airwaysdemobackend.enums.FlightStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightSearchDto {
    private Long id;
    private FlightDirection flightDirection;
    private FlightStatus flightStatus;
    private String flightNo;
    @JsonProperty("airline")
    private String airline;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private long duration;
    private String  arrivalPortName;
    private String departurePortName;
    private String departurePortCity;
    private String arrivalPortCity;
    private int totalSeat;
    private int availableSeat;
    private List<ClassDto> classes;
}
