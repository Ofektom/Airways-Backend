package org.ofektom.airwaysdemobackend.controller;


import org.ofektom.airwaysdemobackend.dto.PassengerDTo;
import org.ofektom.airwaysdemobackend.service.PassengerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/passenger")
public class PassengerController {
   private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping("/get-passengers")
    public ResponseEntity<Set<PassengerDTo>> getUsers (){
        Set<PassengerDTo> passengers= passengerService.findAll();
        return  ResponseEntity.ok(passengers);
    }
}
