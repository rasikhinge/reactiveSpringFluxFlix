package org.cinema.fluxflixservice.dao;

import org.cinema.fluxflixservice.beans.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository
{
    void loadData(List<Movie> movies1);

    void removeAll();

    Optional<List<Movie>> getAll();

   Optional<Movie> getById(int id);

    Optional<Movie> getByTitle(String title);

    Movie save(Movie movie);
}
