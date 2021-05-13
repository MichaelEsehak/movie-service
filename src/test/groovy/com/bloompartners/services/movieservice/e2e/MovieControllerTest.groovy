package com.bloompartners.services.movieservice.e2e

import com.bloompartners.services.movieservice.converter.MovieConverter
import com.bloompartners.services.movieservice.document.Movie
import com.bloompartners.services.movieservice.e2e.E2ESpecification
import com.bloompartners.services.movieservice.model.MovieModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

/**
 * @author melyas
 */

class MovieControllerTest extends E2ESpecification {

    WebTestClient webTestClient

    @Autowired
    MovieConverter movieConverter

    def movieId = "TestId"

    def setup() {
        webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:8080/api/v1.0/movies")
                .build()
        movieService.deleteById(movieId).block()
    }

    def "When a movie is in the db it should be returned through the endpoint"() {

        when: "a movie entity is created"
        def movie = createNewMovie(movieId, "test movie")

        and: "the entity is persisted to db"
        movieService.createMovie(movieConverter.convertToMovieModel(movie)).block()

        and: "the entity is retrieved through the endpoint"
        def result = webTestClient.get()
                .uri("/id/" + movieId)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Movie)
                .returnResult()

        then: "the returned entity id should equal the persisted enity id"
        result.responseBody.getId() == movie.getId()
    }

    def "When a movie is in Not in the db 404 should be expected"() {

        when: "a movie entity is created"
        def movie = createNewMovie(movieId, "test movie")

        and: "different entity is persisted to db"
        movieService.createMovie(movieConverter.convertToMovieModel(movie)).block()

        then: "404 should be expected"
        webTestClient.get()
                .uri("/id/notfound")
                .exchange()
                .expectStatus().isNotFound()

    }

    def "When a movie is in Not in db, create end point should work normally"() {

        when: "a movie entity is created and it is not in db"
        def movie = createMovieModel(movieId, "test movie")

        and: "200 should be expected with movieModel with version =1"
        def result =webTestClient.post()
                .uri("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(movie)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(MovieModel)
                .returnResult()
        then:
        result.responseBody.getId()==movieId
        result.responseBody.getVersion()==movie.getVersion()


    }

    def "When a movie is IN db, create end point should return NOT_ACCEPTABLE"() {

        when: "a movie entity is created and it is  in db"
        def movieModel = createMovieModel(movieId, "test movie")
        movieService.createMovie(movieModel).block()

        then: "not accepted should be expected if create is called again"
        webTestClient.post()
                .uri("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(movieModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_ACCEPTABLE)
    }

    def "When a movie is not in db, update endpoint should return Not Found"() {

        when: "a movie entity is created and it is not in db"
        def movieModel = createMovieModel(movieId, "test movie")

        then: "not found should be expected if update is called again"
        webTestClient.post()
                .uri("/update")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(movieModel)
                .exchange()
                .expectStatus().isNotFound()
    }

    def "When a movie is IN db, update end point should return work and return updated version"() {

        when: "a movie entity is created and it is  in db"
        def movieModel = createMovieModel(movieId, "test movie")
        movieService.createMovie(movieModel).block()

        then: "200 should returned with movie version increased"
        def result =webTestClient.post()
                .uri("/update")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(movieModel)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(MovieModel)
                .returnResult()
        then:
        result.responseBody.getId()==movieId
        result.responseBody.getVersion()==movieModel.getVersion()+1
    }

    def "When a movie is IN db, update endpoint should not work if version doesn't match"() {

        when: "a movie entity is created and it is  in db"
        def movieModel = createMovieModel(movieId, "test movie")
        movieService.createMovie(movieModel).block()
        movieModel.setVersion(2)

        then: "Bad request should be expected if update is called"
        webTestClient.post()
                .uri("/update")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(movieModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
    }
}