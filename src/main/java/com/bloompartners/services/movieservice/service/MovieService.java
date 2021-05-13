package com.bloompartners.services.movieservice.service;

import com.bloompartners.services.movieservice.model.MovieModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author melyas
 */
public interface MovieService {


    Mono<MovieModel> loadMovieById(String s);

    Mono<MovieModel> updateMovie(MovieModel movieModel);

    Mono<MovieModel> createMovie(MovieModel movieModel);

    Mono<Void> deleteById(String id);

    Flux<MovieModel> loadAllMovies(Long offset, Long limit);

    Flux<MovieModel> loadMoviesByAttributes(String name, Long duration,
        Integer releaseYear, Long offset, Long limit);
}
