package org.ofektom.airwaysdemobackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException(String message) {super(message);

    }
}
