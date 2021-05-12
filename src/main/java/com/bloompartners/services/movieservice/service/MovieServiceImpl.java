package com.bloompartners.services.movieservice.service;

import com.bloompartners.services.movieservice.repository.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author melyas
 */

@Service
@AllArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService{

    MovieRepository movieRepository;

}
