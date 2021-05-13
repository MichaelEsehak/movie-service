package com.bloompartners.services.movieservice.junit

import com.bloompartners.services.movieservice.converter.MovieConverter
import com.bloompartners.services.movieservice.document.Movie
import com.bloompartners.services.movieservice.exception.MovieNotFoundException
import com.bloompartners.services.movieservice.model.MovieModel
import com.bloompartners.services.movieservice.repository.MovieRepository
import com.bloompartners.services.movieservice.service.MovieService
import com.bloompartners.services.movieservice.service.MovieServiceImpl
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

import static com.bloompartners.services.movieservice.TestUtils.createMovieModel
import static com.bloompartners.services.movieservice.TestUtils.createNewMovie

/**
 * @author melyas
 */
class MovieServiceTest extends Specification {

    MovieRepository movieRepo = Mock(MovieRepository)
    MovieConverter movieConverter = Mock(MovieConverter)
    MovieService movieService
    String movieId

    def setup() {


    }


    def "createMovie when called should create movie if movie is not in db"() {
        when: "movie is not in db"
        movieId = "Test1"
        Movie movie = createNewMovie(movieId, "test name")
        MovieModel movieModel = createMovieModel(movieId, "test name")

        MovieRepository movieRepo = Mock(MovieRepository) {
            1 * findById(_) >> Mono.empty()
            1 * save(_) >> Mono.just(movie)
        }
        MovieConverter movieConverter = Mock(MovieConverter) {
            1 * convertToMovie(movieModel) >> movie
            1 * convertToMovieModel(movie) >> movieModel
        }
        movieService = new MovieServiceImpl(movieRepo, movieConverter)
        def result=movieService.createMovie(movieModel).blockOptional()

        then:
        result.isPresent()
    }

    def "update Movie when it is in db and version doesn't conflict"() {

        when: "movie is  in db"
        movieId = "Test1"
        Movie movie = createNewMovie(movieId, "test name")
        MovieModel movieModel = createMovieModel(movieId, "test name")

        MovieRepository movieRepo = Mock(MovieRepository) {
            1 * findById(_) >> Mono.just(movie)
            1 * save(_) >> Mono.just(movie)
        }
        MovieConverter movieConverter = Mock(MovieConverter) {
            1 * convertToMovie(movieModel) >> movie
            1 * convertToMovieModel(movie) >> movieModel
        }
        movieService = new MovieServiceImpl(movieRepo, movieConverter)
        def result=movieService.updateMovie(movieModel).blockOptional()

        then:
        result.isPresent()
    }

    def "Update should return not found when movie is not in db"() {
        when: "movie is not in db"
        movieId = "Test1"
        Movie movie = createNewMovie(movieId, "test name")
        MovieModel movieModel = createMovieModel(movieId, "test name")

        MovieRepository movieRepo = Mock(MovieRepository) {
            1 * findById(_) >> Mono.empty()
            0 * save(_) >> Mono.just(movie)
        }
        MovieConverter movieConverter = Mock(MovieConverter) {
            1 * convertToMovie(movieModel) >> movie
            0 * convertToMovieModel(movie) >> movieModel
        }
        movieService = new MovieServiceImpl(movieRepo, movieConverter)

        then:
        StepVerifier.create(movieService.updateMovie(movieModel)).expectError(MovieNotFoundException.class)
    }
}
