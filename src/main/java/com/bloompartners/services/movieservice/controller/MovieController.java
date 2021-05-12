package com.bloompartners.services.movieservice.controller;

import com.bloompartners.services.movieservice.document.Movie;
import com.bloompartners.services.movieservice.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movie")
@AllArgsConstructor
public class MovieController {

    MovieService movieService;

    @GetMapping("/id/{id}")
    public Mono<Movie> getMovie(@PathVariable("id") String id) {
        return movieService.loadMovieById(id);


    }
}