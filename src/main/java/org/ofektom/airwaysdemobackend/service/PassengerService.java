package org.ofektom.airwaysdemobackend.service;



import org.ofektom.airwaysdemobackend.dto.PassengerDTo;

import java.util.Set;

public interface PassengerService {
     Set<PassengerDTo> findAll();
}
