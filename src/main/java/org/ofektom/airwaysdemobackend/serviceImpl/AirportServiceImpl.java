package org.ofektom.airwaysdemobackend.serviceImpl;


import jakarta.transaction.Transactional;
import org.ofektom.airwaysdemobackend.exception.AirportNotFoundException;
import org.ofektom.airwaysdemobackend.model.Airport;
import org.ofektom.airwaysdemobackend.repository.AirportRepository;
import org.ofektom.airwaysdemobackend.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional()
public class AirportServiceImpl implements AirportService {

    @Autowired
    private AirportRepository airportRepo;

    @Override
    public Airport getAirportById(String airportId) {
        return airportRepo.findById(airportId).orElseThrow(() -> new AirportNotFoundException(airportId));
    }

    @Override
    public List<Airport> getAllAirports() {
        return airportRepo.findAll();
    }

}