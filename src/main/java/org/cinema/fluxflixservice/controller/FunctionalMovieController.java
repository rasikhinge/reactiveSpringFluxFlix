package org.cinema.fluxflixservice.controller;

import org.cinema.fluxflixservice.beans.Movie;
import org.cinema.fluxflixservice.beans.MovieEvent;
import org.cinema.fluxflixservice.service.MovieService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Component
public class FunctionalMovieController {
    @Bean
    RouterFunction<ServerResponse> routerFunction(MovieHandler movieHandler) {
        return RouterFunctions.route(GET("/movie"), request -> movieHandler.all(request))
                .andRoute(GET("/movie/{id}"), request -> movieHandler.byId(request))
                .andRoute(GET("/movie/{id}/events"), request -> movieHandler.events(request));
    }

}

@Component
class MovieHandler {

    private final MovieService movieService;

    MovieHandler(MovieService movieService) {
        this.movieService = movieService;
    }

    public Mono<ServerResponse> all(ServerRequest request) {
        return ServerResponse.ok()
                .body(movieService.getAll(), Movie.class);
    }

    public Mono<ServerResponse> byId(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        return ServerResponse.ok()
                .body(movieService.getById(id), Movie.class);
    }

    public Mono<ServerResponse> events(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(movieService.getEvents(id), MovieEvent.class);
    }
}