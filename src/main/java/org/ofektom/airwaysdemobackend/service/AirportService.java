package org.ofektom.airwaysdemobackend.service;




import org.ofektom.airwaysdemobackend.model.Airport;

import java.util.List;


public interface AirportService {
    Airport getAirportById(String airportId);
    List<Airport> getAllAirports();
}
