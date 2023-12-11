package com.codinghavoc.monolith.mmdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.mmdb.entity.Actor;
import com.codinghavoc.monolith.mmdb.exception.ActorNotFoundException;
import com.codinghavoc.monolith.mmdb.repo.ActorRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ActorSvcImpl implements ActorSvc {
    ActorRepo repo;

    @Override
    public Actor getActor(Long id){
        Optional<Actor> actor = repo.findById(id);
        return unwrapCourse(actor, id);
    }

    @Override
    public List<Actor> getAllActors(){
        return (List<Actor>)repo.findAll();
    }

    @Override
    public List<Actor> getActorsByMovieId(Long movieId){
        return (List<Actor>) repo.findActorByMovieId(movieId);
    }
    
    static Actor unwrapCourse(Optional<Actor> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new ActorNotFoundException(id);
    }    
}
