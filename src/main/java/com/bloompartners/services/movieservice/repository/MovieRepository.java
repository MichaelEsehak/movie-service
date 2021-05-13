package com.bloompartners.services.movieservice.repository;

import com.bloompartners.services.movieservice.document.Movie;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author melyas
 */
@Repository
public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {






}
