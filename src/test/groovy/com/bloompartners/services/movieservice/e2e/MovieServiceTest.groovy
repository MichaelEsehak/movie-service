package com.bloompartners.services.movieservice.e2e

import com.bloompartners.services.movieservice.converter.MovieConverter
import com.bloompartners.services.movieservice.service.MovieService
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author melyas
 */
class MovieServiceTest extends E2ESpecification{

    @Autowired
    MovieService movieService
    @Autowired
    MovieConverter movieConverter

    def "Test save movie to db"(){
        given: "a movie"
        def movie=createNewMovie("TEEST1","movie name")
        when: "save movie is called"
        movieService.saveOrUpdate(movieConverter.convertToMovieModel(movie)).block()

        and: "movie is retrieved back"
        def dbMovie=movieService.loadMovieById(movie.getId()).blockOptional()

        then:
        dbMovie.isPresent()


    }
}
