package com.bloompartners.services.movieservice

import com.bloompartners.services.movieservice.document.Movie
import com.bloompartners.services.movieservice.model.MovieModel

/**
 * @author melyas
 */
class TestUtils {

    public static Movie createNewMovie(String id, String name, version = 1) {
        Movie movie = new Movie()
        movie.setId(id)
        movie.setName(name)
        movie.setVersion(version)

        return movie;

    }

    public static MovieModel createMovieModel(String id, String name, version = 1) {
        MovieModel movieModel = new MovieModel()
        movieModel.setId(id)
        movieModel.setName(name)
        movieModel.setVersion(version)
        return movieModel;

    }
}
