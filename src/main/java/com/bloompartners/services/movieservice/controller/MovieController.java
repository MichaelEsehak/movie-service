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
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1.0/movie")
@AllArgsConstructor
public class MovieController {

    MovieService movieService;

    @PostMapping("/update")
    ResponseEntity<Mono<MovieModel>> createUpdateMovie(@RequestBody MovieModel movieModel) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(movieService.saveOrUpdate(movieModel));

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Mono<MovieModel>> getMovie(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(movieService.loadMovieById(id));

    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") String id) {
        movieService.deleteById(id).subscribe();
        return ResponseEntity.noContent().build();
    }
}