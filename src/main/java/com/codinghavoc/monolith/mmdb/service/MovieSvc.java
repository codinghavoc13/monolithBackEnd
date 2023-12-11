package com.codinghavoc.monolith.mmdb.service;

import java.util.List;

import com.codinghavoc.monolith.mmdb.entity.Movie;

public interface MovieSvc {
    Movie getMovie(Long id);
    List<Movie> getAllMovies();
    Movie addActorToMovie(Long actorId, Long movieId);
    List<Movie> getMoviesByActorId(Long actorId);
    // List<Genre> getGenresByMovieId(Long movieId);
}
