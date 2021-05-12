package com.bloompartners.services.movieservice.service;

import static java.lang.String.format;

import com.bloompartners.services.movieservice.converter.MovieConverter;
import com.bloompartners.services.movieservice.document.Movie;
import com.bloompartners.services.movieservice.exception.MovieNotFoundException;
import com.bloompartners.services.movieservice.model.MovieModel;
import com.bloompartners.services.movieservice.repository.MovieRepository;
import java.util.ConcurrentModificationException;
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
    public Mono<MovieModel> saveOrUpdate(MovieModel movieModel) {
        Movie movie = movieConverter.convertToMovie(movieModel);

        return movieRepository.findById(movie.getId())
            .flatMap(existingDocument -> {
                if (!existingDocument.getVersion().equals(movie.getVersion())) {
                    return Mono.error(new ConcurrentModificationException(
                        format("Version doesn't match, existing (%s), requested (%s)",
                            existingDocument.getVersion(), movie.getVersion())
                    ));
                }
                return movieRepository.save(movie);
            })
            .switchIfEmpty(
                Mono.defer(() -> movieRepository.save(movie)))
            .doOnNext(movieEntity -> log
                .info("Movie with Id: {} processed Successfully", movieEntity.getId()))
            .map(movieConverter::convertToMovieModel);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return movieRepository.deleteById(id);
    }
}
