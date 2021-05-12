package com.bloompartners.services.movieservice.converter;

import com.bloompartners.services.movieservice.document.Actor;
import com.bloompartners.services.movieservice.document.Movie;
import com.bloompartners.services.movieservice.exception.MissingDataException;
import com.bloompartners.services.movieservice.model.ActorModel;
import com.bloompartners.services.movieservice.model.MovieModel;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * @author melyas
 */
@Component
public class MovieConverter {

    public Movie convertToMovie(MovieModel model) {

        validateMovieInputs(model);

        Movie movie = new Movie();
        movie.setId(model.getId());
        movie.setVersion(model.getVersion());
        movie.setName(model.getName());
        movie.setReleaseYear(model.getReleaseYear());
        movie.setDuration(model.getDuration());
        movie.setActors(Optional.ofNullable(model.getActorsModel())
            .map((list -> list.stream().map(this::convertToActor).collect(Collectors.toList())))
            .orElse(
                Collections.emptyList()));
        movie.setThumbnailUrl(model.getThumbnailUrl());
        movie.setGenres(model.getGenres());
        movie.setRate(model.getRate());
        movie.setLanguage(model.getLanguage());
        movie.setCountry(model.getCountry());
        return movie;
    }

    private Actor convertToActor(ActorModel model) {
        Actor actor = new Actor();
        actor.setId(model.getId());
        actor.setName(model.getName());
        return actor;
    }

    private void validateMovieInputs(MovieModel movie) {
        if (Objects.isNull(movie.getId()) || Objects.isNull(movie.getName())) {
            throw new MissingDataException("Movie id and name shouldn't be null");
        }
    }

    public MovieModel convertToMovieModel(Movie movie) {
        MovieModel movieModel = new MovieModel();
        movieModel.setId(movie.getId());
        movieModel.setVersion(movie.getVersion());
        movieModel.setName(movie.getName());
        movieModel.setReleaseYear(movie.getReleaseYear());
        movieModel.setDuration(movie.getDuration());
        movieModel.setActorsModel(Optional.ofNullable(movie.getActors()).map(
            (list -> list.stream().map(this::convertToActorModel).collect(Collectors.toList())))
            .orElse(
                Collections.emptyList()));
        movieModel.setThumbnailUrl(movie.getThumbnailUrl());
        movieModel.setGenres(movie.getGenres());
        movieModel.setRate(movie.getRate());
        movieModel.setLanguage(movie.getLanguage());
        movieModel.setCountry(movie.getCountry());
        return movieModel;
    }

    private ActorModel convertToActorModel(Actor actor) {
        ActorModel actorModel = new ActorModel();
        actorModel.setId(actor.getId());
        actorModel.setName(actor.getName());
        return actorModel;
    }

}
