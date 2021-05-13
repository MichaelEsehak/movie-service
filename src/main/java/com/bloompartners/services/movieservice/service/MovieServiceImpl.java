package com.bloompartners.services.movieservice.service;

import static java.lang.String.format;

import com.bloompartners.services.movieservice.converter.MovieConverter;
import com.bloompartners.services.movieservice.document.Movie;
import com.bloompartners.services.movieservice.exception.MovieAlreadyExistsException;
import com.bloompartners.services.movieservice.exception.MovieNotFoundException;
import com.bloompartners.services.movieservice.model.MovieModel;
import com.bloompartners.services.movieservice.repository.MovieRepository;
import java.util.ConcurrentModificationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author melyas
 */

@Service
@AllArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    MovieRepository movieRepository;
    MovieConverter movieConverter;

    @Override
    public Mono<MovieModel> loadMovieById(String id) {
        return movieRepository.findById(id)
            .map(movieConverter::convertToMovieModel)
            .switchIfEmpty(
                Mono.defer(() -> Mono.error(new MovieNotFoundException(
                    String.format("Movie with id %s doesn't exists", id))))
            );
    }

    @Override
    public Mono<MovieModel> updateMovie(MovieModel movieModel) {
        Movie movie = movieConverter.convertToMovie(movieModel);

        return movieRepository.findById(movie.getId())
            .flatMap(existingDocument -> {
                if (!existingDocument.getVersion().equals(movie.getVersion())) {
                    return Mono.error(new ConcurrentModificationException(
                        format("Version doesn't match, existing (%s), requested (%s)",
                            existingDocument.getVersion(), movie.getVersion())
                    ));
                }
                movie.setVersion(existingDocument.getVersion() + 1);
                return movieRepository.save(movie);
            })
            .switchIfEmpty(
                Mono.defer(() -> Mono.error(new MovieNotFoundException(
                    String.format("Movie with id %s doesn't exists", movie.getId())))))
            .doOnNext(movieEntity -> log
                .info("Movie with Id: {} processed Successfully", movieEntity.getId()))
            .map(movieConverter::convertToMovieModel);
    }

    @Override
    public Mono<MovieModel> createMovie(MovieModel movieModel) {
        Movie movie = movieConverter.convertToMovie(movieModel);
        movie.setVersion(1L);
        return movieRepository.findById(movie.getId())
            .flatMap(existingDocument -> {
                if (existingDocument.getId().equals(movie.getId())) {
                    return Mono.error(new MovieAlreadyExistsException(
                        format("Movie with Id %s already exists",
                            movie.getId())
                    ));
                }
                return movieRepository.save(movie);
            })
            .switchIfEmpty(
                Mono.defer(
                    () -> movieRepository.save(movie)))
            .doOnNext(movieEntity -> log
                .info("Movie with Id: {} processed Successfully", movieEntity.getId()))
            .map(movieConverter::convertToMovieModel);

    }

    @Override
    public Mono<Void> deleteById(String id) {
        return movieRepository.deleteById(id);
    }

    @Override
    public Flux<MovieModel> loadAllMovies(Long offset, Long limit) {
        return movieRepository.findAll()
            .skip(offset)
            .take(limit)
            .map(movieConverter::convertToMovieModel);
    }

    @Override
    public Flux<MovieModel> loadMoviesByAttributes(String name, Long duration,
        Integer releaseYear, Long offset, Long limit) {

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
            .withIgnoreNullValues()
            .withStringMatcher(StringMatcher.CONTAINING)
            .withIgnoreCase().withIgnoreNullValues();

        Movie movie = new Movie();
        movie.setName(name);
        movie.setDuration(duration);
        movie.setReleaseYear(releaseYear);

        return movieRepository.findAll(Example.of(movie, matcher)).skip(offset).take(limit)
            .map(movieConverter::convertToMovieModel);

    }

}
