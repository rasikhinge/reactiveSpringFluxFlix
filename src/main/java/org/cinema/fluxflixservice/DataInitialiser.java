package org.cinema.fluxflixservice;

import org.cinema.fluxflixservice.beans.Movie;
import org.cinema.fluxflixservice.dao.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DataInitialiser implements CommandLineRunner {

    private final MovieRepository repository;

    public DataInitialiser(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        Flux<Movie> movieFlux = Flux.just("Chennai Express", "Thor", "Singham", "Chak De India")
                .map(title -> new Movie(title.length(), title))
                .flatMap(m -> Mono.just(repository.save(m)));
        movieFlux.subscribe(System.out::println);

        repository.getAll().ifPresent(lst -> lst.forEach(System.out::println));
    }
}
