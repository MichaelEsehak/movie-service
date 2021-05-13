package com.bloompartners.services.movieservice.e2e

import com.bloompartners.services.movieservice.converter.MovieConverter
import com.bloompartners.services.movieservice.exception.MovieAlreadyExistsException
import com.bloompartners.services.movieservice.exception.MovieNotFoundException
import com.bloompartners.services.movieservice.service.MovieService
import org.springframework.beans.factory.annotation.Autowired
import reactor.test.StepVerifier

import static com.bloompartners.services.movieservice.TestUtils.createNewMovie

/**
 * @author melyas
 */
class MovieServiceTest extends E2ESpecification {

    @Autowired
    MovieService movieService
    @Autowired
    MovieConverter movieConverter

    def movieId = "TEST1"

    def setup() {
        movieService.deleteById(movieId).block()
    }

    def "Test save new movie to db when it is first created"() {

        given: "a movie"
        def movie = createNewMovie(movieId, "movie name")
        when: "save movie is called"
        movieService.createMovie(movieConverter.convertToMovieModel(movie)).block()
        then: "movie is retrieved back"
        StepVerifier.create(movieService.loadMovieById(movieId))
                .expectNextCount(1)

    }

    def "exception should happen if create is called and same id is in db"() {
        given: "a movie"
        def movie = createNewMovie(movieId, "movie name")
        when: "save movie is called"
        movieService.createMovie(movieConverter.convertToMovieModel(movie)).block()

        then: "MovieAlreadyExistsException should be thrown when the create is called again"
        StepVerifier.create(movieService.createMovie(movieConverter.convertToMovieModel(movie)))
                .expectError(MovieAlreadyExistsException.class).verify()

    }

    def "update movie should throw exception if movie doesn't exists"() {

        when: "a movie entity that is not in db"
        def movie = createNewMovie(movieId, "movie name")
        then: "Not found exception should thrown when trying to update "
        StepVerifier.create(movieService.updateMovie(movieConverter.convertToMovieModel(movie)))
                .expectError(MovieNotFoundException.class).verify()

    }

    def "update movie should throw concurrent exception  if movie version is different"() {

        given: "a movie entity that is in db with version =1"
        def movie = createNewMovie(movieId, "movie name")
        movieService.createMovie(movieConverter.convertToMovieModel(movie)).block()

        when: "version doesn't match"
        movie.setVersion(2)

        then: "ConcurrentModificationException should be thrown when trying to update "
        StepVerifier.create(movieService.updateMovie(movieConverter.convertToMovieModel(movie)))
                .expectError(ConcurrentModificationException.class).verify()
    }

    def "Load movie should success if it is in db"() {

        when: "a movie entity that is in db with version =1"
        def movie = createNewMovie(movieId, "movie name")
        movieService.createMovie(movieConverter.convertToMovieModel(movie)).block()

        then: "list count should be 1"
        StepVerifier.create(movieService.loadMovieById(movieId))
                .expectNextCount(1)
    }

    def "Load movie should return nothing if it is not in db"() {

        when: "a movie entity that is in db with version =1"
        def movie = createNewMovie(movieId, "movie name")
        movieService.createMovie(movieConverter.convertToMovieModel(movie)).block()

        then: "list count should be 0"
        StepVerifier.create(movieService.loadMovieById("movie2"))
                .expectNextCount(0)
    }

    def "delete movie should not return error if it is not in db"() {

        when: "a movie entity that is in db "

        then: "subscription should work normally"
        StepVerifier.create(movieService.deleteById(movieId))
                .expectSubscription()
    }

    def "delete movie should delete it if the movie in db"() {

        given: "a movie entity that is in db "
        def movie = createNewMovie(movieId, "movie name")
        movieService.createMovie(movieConverter.convertToMovieModel(movie)).block()

        when: "movie is deleted"
        movieService.deleteById(movieId).block()

        then: "movie is not in db any more"
        StepVerifier.create(movieService.loadMovieById(movieId))
                .expectNextCount(0)
    }

}
