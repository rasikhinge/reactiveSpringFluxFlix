package org.cinema.fluxflixservice.service;

import org.cinema.fluxflixservice.beans.Movie;
import org.cinema.fluxflixservice.beans.MovieEvent;
import org.cinema.fluxflixservice.dao.MovieRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;

import static java.util.Collections.emptyList;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;

    public MovieServiceImpl(MovieRepository repository) {
        this.repository = repository;
    }


    @Override
    public Flux<Movie> getAll() {
        return Flux.fromIterable(repository.getAll().orElse(emptyList()));
    }

    @Override
    public Mono<Movie> getById(int id) {
        return Mono.just(repository.getById(id).orElse(null));
    }

    @Override
    public Mono<Movie> getByTitle(String title) {
        return Mono.just(repository.getByTitle(title).orElse(null));
    }

    @Override
    public Mono<Movie> save(Movie movie) {
        return Mono.just(repository.save(movie));
    }

    @Override
    public Flux<MovieEvent> getEvents(int movieId) {
        return Flux.<MovieEvent>generate(sink -> sink.next(new MovieEvent(movieId, new Date())))
                .delayElements(Duration.ofSeconds(1L));
    }
}
