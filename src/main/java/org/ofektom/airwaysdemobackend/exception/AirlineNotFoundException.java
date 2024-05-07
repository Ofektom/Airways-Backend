package org.ofektom.airwaysdemobackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AirlineNotFoundException extends RuntimeException{
    public AirlineNotFoundException(String message) {
        super(message);
    }
}