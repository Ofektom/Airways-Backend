package org.ofektom.airwaysdemobackend.controller;

import org.ofektom.airwaysdemobackend.model.Airport;
import org.ofektom.airwaysdemobackend.service.AirportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportController {

    @Autowired
    private AirportService airportService;

    private static final Logger logger = LoggerFactory.getLogger(AirportController.class);

    @GetMapping
    public @ResponseBody List<Airport> getAllAirports() {
        return airportService.getAllAirports();
    }

    @GetMapping("/{iata-code}")
    public @ResponseBody Airport getAirportById(@PathVariable("iata-code") String iataCode) {
        return airportService.getAirportById(iataCode);
    }

}
