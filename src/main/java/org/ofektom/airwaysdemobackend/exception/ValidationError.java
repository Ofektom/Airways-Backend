package org.ofektom.airwaysdemobackend.exception;

public record ValidationError(
        String field,
        String message
) {
}