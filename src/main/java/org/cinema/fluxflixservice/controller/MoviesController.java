package org.cinema.fluxflixservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoviesController {

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome..";
    }
}
