package com.bloompartners.services.movieservice.exception;

import java.time.Instant;
import java.util.ConcurrentModificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author melyas
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConcurrentModificationException.class)
    public ResponseEntity<Object> handleConcurrentModificationException(ConcurrentModificationException exception) {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(new ErrorResponse(
            Instant.now(),HttpStatus.BAD_REQUEST.value(),exception.getMessage()));
    }

    @ExceptionHandler(MissingDataException.class)
    public ResponseEntity<Object> handleMissingDataException(MissingDataException exception) {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(new ErrorResponse(
            Instant.now(),HttpStatus.BAD_REQUEST.value(),exception.getMessage()));
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<Object> handleMovieNotFoundException(MovieNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new ErrorResponse(
            Instant.now(),HttpStatus.NOT_FOUND.value(),exception.getMessage()));
    }

}
