package com.codinghavoc.monolith.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codinghavoc.monolith.entity.Movie;
import com.codinghavoc.monolith.service.MovieSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {
    private MovieSvc svc;
    
    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies(){
        return new ResponseEntity<>(this.svc.getAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id){
        return new ResponseEntity<>(this.svc.getMovie(id),HttpStatus.OK);
    }
    
    @PutMapping("/{movieId}/actor/{actorId}")
    public ResponseEntity<Movie> addActorToMovie(@PathVariable Long movieId, @PathVariable Long actorId){
        return new ResponseEntity<>(svc.addActorToMovie(actorId, movieId), HttpStatus.OK);
    }

    @GetMapping("/findByActorId/{actorId}")
    public ResponseEntity<List<Movie>> getMoviesByActorId(@PathVariable Long actorId){
        return new ResponseEntity<>(svc.getMoviesByActorId(actorId), HttpStatus.OK);
    }
    
}
