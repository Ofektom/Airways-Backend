package org.ofektom.airwaysdemobackend.serviceImpl;

import org.ofektom.airwaysdemobackend.dto.FlightSearchDto;
import org.ofektom.airwaysdemobackend.exception.FlightNotFoundException;
import org.ofektom.airwaysdemobackend.model.Airport;
import org.ofektom.airwaysdemobackend.model.Flight;
import org.ofektom.airwaysdemobackend.repository.FlightRepository;
import org.ofektom.airwaysdemobackend.service.FlightService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;

    }

    @Override
    public String deleteFlight(Long Id) {
        Flight flight = flightRepository.findById(Id)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found"));
        flightRepository.delete(flight);
        return "Flight deleted successfully";

    }

    @Override
    public List<FlightSearchDto> searchAvailableFlight(Airport departurePort, Airport arrivalPort, LocalDate departureDate, LocalDate returnDate, int noOfAdult, int noOfChildren, int noOfInfant) {
        List<Flight> availableFlight;
        if(returnDate== null){
            availableFlight = flightRepository.findByDeparturePortAndArrivalPortAndDepartureDateAndNoOfAdultGreaterThanEqualAndNoOfChildrenGreaterThanEqualAndNoOfInfantGreaterThanEqual(  departurePort,  arrivalPort,  departureDate, noOfAdult, noOfChildren, noOfInfant);
        }else{
            availableFlight = flightRepository.findByDeparturePortAndArrivalPortAndDepartureDateAndReturnDateAndNoOfAdultGreaterThanEqualAndNoOfChildrenGreaterThanEqualAndNoOfInfantGreaterThanEqual( departurePort,   arrivalPort,  departureDate,  returnDate,  noOfAdult,  noOfChildren,  noOfInfant);
        }
        if(availableFlight.isEmpty()){
            throw new FlightNotFoundException("No flight found for specified criteria. Please adjust search parameters");
        }
        List<FlightSearchDto> availableFlightDTOs = new ArrayList<>();
        for (Flight flight : availableFlight) {
            FlightSearchDto flightDTO = new FlightSearchDto();
            flightDTO.setId(flight.getId());
            flightDTO.setFlightDirection(flight.getFlightDirection());
            flightDTO.setFlightNo(flight.getFlightNo());
            flightDTO.setAirline(flight.getAirline().getName());
            flightDTO.setArrivalDate(flight.getArrivalDate());
            flightDTO.setDepartureDate(flight.getDepartureDate());
            flightDTO.setArrivalTime(flight.getArrivalTime());
            flightDTO.setReturnDate(flight.getReturnDate());
            flightDTO.setReturnTime(flight.getReturnTime());
            flightDTO.setDepartureTime(flight.getDepartureTime());
            flightDTO.setDuration(flight.getDuration());
            flightDTO.setArrivalPort(flight.getArrivalPort());
            flightDTO.setDeparturePort(flight.getDeparturePort());
            flightDTO.setClasses(flight.getClasses());
            flightDTO.setTotalSeat(flight.getTotalSeat());
            flightDTO.setAvailableSeat(flight.getAvailableSeat());
            flightDTO.setNoOfChildren(flight.getNoOfChildren());
            flightDTO.setNoOfAdult(flight.getNoOfAdult());
            flightDTO.setNoOfInfant(flight.getNoOfInfant());


            availableFlightDTOs.add(flightDTO);
        }

        return availableFlightDTOs;
    }

    @Override
    public Page<Flight> getAllFlights(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return flightRepository.findAll(pageable);
    }
    @Override
    public int getTotalNumberOfFlights() {
        return (int) flightRepository.count();
    }
}
