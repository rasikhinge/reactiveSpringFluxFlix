package org.cinema.fluxflixservice.service;

import org.cinema.fluxflixservice.beans.Movie;
import org.cinema.fluxflixservice.beans.MovieEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieService {

    Flux<Movie> getAll();

    Mono<Movie> getById(int id);

    Mono<Movie> getByTitle(String title);

    Mono<Movie> save(Movie movie);

    Flux<MovieEvent> getEvents(int movieId);
}
