package com.codinghavoc.monolith.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.entity.Actor;

public interface ActorRepo extends CrudRepository<Actor,Long>{
    static String selectAllActorByMovieId = "select a.first_name, " +
        "	a.last_name, " +
        "	a.actor_id, " +
        "from monolithdb.actors as a " +
        "join monolithdb.actor_movie as am " +
        "	on a.actor_id=am.actor_id " +
        "where am.movie_id=?1 "+
        "order by a.last_name asc";
    @Query(value=selectAllActorByMovieId, nativeQuery = true)
    List<Actor> findActorByMovieId(Long id);
    
}