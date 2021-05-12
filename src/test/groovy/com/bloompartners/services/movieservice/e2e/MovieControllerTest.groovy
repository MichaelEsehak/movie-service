package com.bloompartners.services.movieservice.e2e


import com.bloompartners.services.movieservice.document.Movie
import com.bloompartners.services.movieservice.e2e.E2ESpecification
import org.springframework.test.web.reactive.server.WebTestClient
/**
 * @author melyas
 */

class MovieControllerTest extends E2ESpecification {

    WebTestClient webTestClient


    def setup() {
        webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:8080")
                .build()
    }

    def "When a movie is in the db it should be returned through the endpoint"() {

        when: "a movie entity is created"
        def movie=createNewMovie("1","test movie")

        and: "the entity is persisted to db"
        movieService.saveMovie(movie).block()

        and: "the entity is retrieved through the endpoint"
        def result=webTestClient.get()
                .uri("/movie/id/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Movie)
                .returnResult()

        then: "the returned entity id should equal the persisted enity id"
        result.responseBody.getId()==movie.getId()




    }
}