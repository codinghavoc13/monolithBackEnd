package com.codinghavoc.monolith.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codinghavoc.monolith.entity.MMDBResponse;
import com.codinghavoc.monolith.service.ActorSvc;
import com.codinghavoc.monolith.service.MovieSvc;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@AllArgsConstructor
@RestController
@RequestMapping("/mmdb")
public class MMDBController {
    ActorSvc actorSvc;
    MovieSvc movieSvc;
    
    @GetMapping("/movieDetails/{movieId}")
    public ResponseEntity<MMDBResponse> getMovieDetails(@PathVariable Long movieId){
        MMDBResponse result = new MMDBResponse();
        result.setMovie(movieSvc.getMovie(movieId));
        result.setActors(actorSvc.getActorsByMovieId(movieId));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/actorDetails/{actorId}")
    public ResponseEntity<MMDBResponse> getActorDetails(@PathVariable Long actorId){
        MMDBResponse result = new MMDBResponse();
        result.setActor(actorSvc.getActor(actorId));
        result.setMovies(movieSvc.getMoviesByActorId(actorId));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
