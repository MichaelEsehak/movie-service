package com.bloompartners.services.movieservice.exception;

/**
 * @author melyas
 */
public class MissingDataException extends RuntimeException {

    private final String message;

    public MissingDataException(String message) {
        super(message);
        this.message = message;
    }


}
