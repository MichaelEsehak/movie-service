package com.bloompartners.services.movieservice.service;

import com.bloompartners.services.movieservice.document.Movie;
import com.bloompartners.services.movieservice.model.MovieModel;
import reactor.core.publisher.Mono;

/**
 * @author melyas
 */
public interface MovieService {


    Mono<MovieModel> loadMovieById(String s);

    Mono<MovieModel> updateMovie(MovieModel movieModel);

    Mono<MovieModel> createMovie(MovieModel movieModel);

    Mono<Void> deleteById(String id);
}
