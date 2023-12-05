package com.codinghavoc.monolith.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.entity.Movie;

public interface MovieRepo extends CrudRepository<Movie,Long>{
    static String selectAllMoviesByActorId = "select m.movie_title, "+
        "   m.release_date, "+
        "   m.plot_summary, "+
        "   m.imdb_link, "+
        "   m.movie_id "+
        "from main.movies as m "+
        "join main.actor_movie as am "+
        "   on m.movie_id=am.movie_id "+
        "where am.actor_id=?1";
    @Query(value=selectAllMoviesByActorId, nativeQuery = true)
    List<Movie> findMoviesByActorId(Long id);

    public List<Movie> findAllByOrderByMovieTitleAsc();

    public List<Movie> findAllByOrderByReleaseDateDesc();
    
}
