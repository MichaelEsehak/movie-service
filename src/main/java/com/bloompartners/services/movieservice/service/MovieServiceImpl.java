package com.bloompartners.services.movieservice.service;

import com.bloompartners.services.movieservice.document.Movie;
import com.bloompartners.services.movieservice.repository.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author melyas
 */

@Service
@AllArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService{

    MovieRepository movieRepository;

    @Override
    public Mono<Movie> saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Mono<Movie> loadMovieById(String id) {
        return movieRepository.findById(id);
    }
}
