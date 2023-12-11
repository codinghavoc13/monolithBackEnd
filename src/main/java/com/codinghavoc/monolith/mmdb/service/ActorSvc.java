package com.codinghavoc.monolith.mmdb.service;

import java.util.List;

import com.codinghavoc.monolith.mmdb.entity.Actor;

public interface ActorSvc {
    Actor getActor(Long id);
    List<Actor> getAllActors();
    List<Actor> getActorsByMovieId(Long movieId);
}
