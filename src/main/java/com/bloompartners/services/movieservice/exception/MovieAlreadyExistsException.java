package com.bloompartners.services.movieservice.exception;

/**
 * @author melyas
 */
public class MovieAlreadyExistsException extends RuntimeException {

    private final String message;

    public MovieAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

}
