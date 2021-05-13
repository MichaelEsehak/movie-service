package com.bloompartners.services.movieservice.controller;

import com.bloompartners.services.movieservice.model.MovieModel;
import com.bloompartners.services.movieservice.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1.0/movies")
@AllArgsConstructor
public class MovieController {

    MovieService movieService;

    @PostMapping("/update")
    ResponseEntity<Mono<MovieModel>> updateMovie(@RequestBody MovieModel movieModel) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(movieService.updateMovie(movieModel));
    }

    @PostMapping("/create")
    ResponseEntity<Mono<MovieModel>> createMovie(@RequestBody MovieModel movieModel) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(movieService.createMovie(movieModel));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Mono<MovieModel>> getMovie(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(movieService.loadMovieById(id));

    }

    @GetMapping("/search")
    public ResponseEntity<Flux<MovieModel>> searchMovies(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Long duration,
        @RequestParam(required = false) Integer releaseYear,
        @RequestParam(defaultValue = "0") Long offset,
        @RequestParam(defaultValue = "500") Long limit) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(movieService
                .loadMoviesByAttributes(name, duration, releaseYear, offset, limit));

    }

    @GetMapping("/")
    public ResponseEntity<Flux<MovieModel>> loadAllMovies(
        @RequestParam(defaultValue = "0") Long offset,
        @RequestParam(defaultValue = "500") Long limit) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(movieService
                .loadAllMovies(offset, limit));

    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") String id) {
        movieService.deleteById(id).subscribe();
        return ResponseEntity.noContent().build();
    }
}