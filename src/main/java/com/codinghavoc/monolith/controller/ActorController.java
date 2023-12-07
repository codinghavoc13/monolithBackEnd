package com.codinghavoc.monolith.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codinghavoc.monolith.entity.Actor;
import com.codinghavoc.monolith.service.ActorSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/actors")
public class ActorController {
    private ActorSvc svc;

    @GetMapping("/all")
    public Iterable<Actor> getAllActors(){
        return this.svc.getAllActors();
    }

    @GetMapping("/{id}")
    public Actor getActor(@PathVariable Long id){
        return this.svc.getActor(id);
    }

    @GetMapping("/findByMovieId/{movieId}")
    public ResponseEntity<List<Actor>> getActorsByMovieId(@PathVariable Long movieId){
        return new ResponseEntity<>(svc.getActorsByMovieId(movieId), HttpStatus.OK);
    }    
}
