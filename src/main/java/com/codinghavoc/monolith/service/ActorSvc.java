package com.codinghavoc.monolith.service;

import java.util.List;

import com.codinghavoc.monolith.entity.Actor;

public interface ActorSvc {
    Actor getActor(Long id);
    List<Actor> getAllActors();
    List<Actor> getActorsByMovieId(Long movieId);
}
