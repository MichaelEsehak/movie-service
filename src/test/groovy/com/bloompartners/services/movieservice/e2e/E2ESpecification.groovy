package com.bloompartners.services.movieservice.e2e

import com.bloompartners.services.movieservice.MovieServiceApplication
import com.bloompartners.services.movieservice.document.Movie
import com.bloompartners.services.movieservice.model.MovieModel
import com.bloompartners.services.movieservice.service.MovieService
import groovyx.net.http.RESTClient
import groovyx.net.http.Status
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
/**
 * @author melyas
 */

@ContextConfiguration(loader = SpringBootContextLoader.class,classes = [E2EConfiguration.class])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class E2ESpecification extends Specification{

    @Autowired
    MovieService movieService

    protected Movie createNewMovie(String id,String name,version =1 ){
        Movie movie =new Movie()
        movie.setId(id)
        movie.setName(name)
        movie.setVersion(version)

        return movie;

    }

    protected MovieModel createMovieModel(String id, String name, version =1 ){
        MovieModel movieModel =new MovieModel()
        movieModel.setId(id)
        movieModel.setName(name)
        movieModel.setVersion(version)
        return movieModel;

    }

    @Configuration
    @Import(MovieServiceApplication.class)
    static class E2EConfiguration {

        @Bean
        public RESTClient restClient() {
            def client = new RESTClient("http://localhost:8080")
            client.handler.put(Status.FAILURE, client.handler.get(Status.SUCCESS))
            return client
        }
    }
}
