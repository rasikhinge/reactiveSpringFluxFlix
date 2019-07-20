package org.cinema.fluxflixservice.dao;

import org.cinema.fluxflixservice.beans.Movie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
public class MovieRepositoryImpl implements MovieRepository {

    private static List<Movie> movies;

    static {
        movies = new ArrayList<>();
        movies.add(new Movie(1, "End Game"));
        movies.add(new Movie(2, "Infinity Wars"));
        movies.add(new Movie(3, "Dhum Dhadaka"));
        movies.add(new Movie(4, "Ashi Hi Banwabawi "));
        movies.add(new Movie(5, "Time pass"));
        movies.add(new Movie(6, "Time pass 2"));
    }

    @Override
    public void loadData(List<Movie> movies1) {
        movies.addAll(movies1);
    }


    @Override
    public void removeAll() {
        movies.clear();
    }

    @Override
    public Optional<List<Movie>> getAll() {
        return ofNullable(movies);
    }

    @Override
    public Optional<Movie> getById(int id) {
        return movies.stream().filter(m -> id == m.getId()).findFirst();
    }

    @Override
    public Optional<Movie> getByTitle(String title) {
        return movies.stream().filter(m -> title.equals(m.getTitle())).findFirst();
    }

    @Override
    public Movie save(Movie movie) {
        movies.add(movie);
        return movie;
    }
}
