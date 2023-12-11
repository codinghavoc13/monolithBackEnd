package com.codinghavoc.monolith.mmdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.mmdb.entity.Actor;
import com.codinghavoc.monolith.mmdb.entity.Movie;
import com.codinghavoc.monolith.mmdb.exception.MovieNotFoundException;
import com.codinghavoc.monolith.mmdb.repo.ActorRepo;
import com.codinghavoc.monolith.mmdb.repo.MovieRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MovieSvcImpl implements MovieSvc {
    private MovieRepo movieRepo;
    private ActorRepo actorRepo;

    @Override
    public Movie getMovie(Long id) {
        Optional<Movie> movie = movieRepo.findById(id);
        return unwrapMovie(movie,id);
    }

    @Override
    public List<Movie> getAllMovies() {
        return (List<Movie>)movieRepo.findAllByOrderByMovieTitleAsc();
    }

    @Override
    public Movie addActorToMovie(Long actorId, Long movieId){
        Movie movie = getMovie(movieId);
        Optional<Actor>actor = actorRepo.findById(actorId);
        Actor unrwappedActor = ActorSvcImpl.unwrapCourse(actor, actorId);
        movie.getActors().add(unrwappedActor);
        return movieRepo.save(movie);
    }

    @Override
    public List<Movie> getMoviesByActorId(Long actorId){
        return (List<Movie>)movieRepo.findMoviesByActorId(actorId);
    }

    // @Override
    // public List<Genre> getGenresByMovieId(Long movieId){
    //     List<Genre> temp = movieRepo.findGenresByMovieId(movieId);
    //     System.out.println(temp);
    //     return (List<Genre>)movieRepo.findGenresByMovieId(movieId);
    // }
    
    static Movie unwrapMovie(Optional<Movie> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new MovieNotFoundException(id);
    }
    
}
