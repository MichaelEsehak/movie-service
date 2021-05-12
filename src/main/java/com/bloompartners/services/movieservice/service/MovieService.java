package com.bloompartners.services.movieservice.service;

import com.bloompartners.services.movieservice.document.Movie;
import reactor.core.publisher.Mono;

/**
 * @author melyas
 */
public interface MovieService {


    Mono<Movie> saveMovie(Movie movie);

    Mono<Movie> loadMovieById(String s);
}
