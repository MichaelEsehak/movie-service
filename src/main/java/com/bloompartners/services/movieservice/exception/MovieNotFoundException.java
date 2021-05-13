package com.bloompartners.services.movieservice.exception;

/**
 * @author melyas
 */
public class MovieNotFoundException extends RuntimeException {

    private final String message;

    public MovieNotFoundException(String message) {
        super(message);
        this.message = message;
    }

}
