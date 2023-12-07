package com.codinghavoc.monolith.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

// import com.codinghavoc.monolith.entity.Genre;
import com.codinghavoc.monolith.entity.Movie;

public interface MovieRepo extends CrudRepository<Movie,Long>{
    static String selectAllMoviesByActorId = """
        select m.movie_title, 
            m.release_date, 
            m.plot_summary, 
            m.movie_id 
        from monolithdb.movies as m 
        join monolithdb.movie_actor as am 
            on m.movie_id=am.movie_id 
        where am.actor_id=?1
        order by m.release_date asc
            """;
    @Query(value=selectAllMoviesByActorId, nativeQuery = true)
    List<Movie> findMoviesByActorId(Long id);

    // static String selectGenresByMovieId = """
    //     select g.genre, g.genre_id
    //     from main.movies as m
    //     join main.movie_genre as mg on m.movie_id=mg.movie_id
    //     join main.genre as g on mg.genre_id=g.genre_id
    //     where m.movie_id = ?1
    //         """;
    // @Query(value=selectGenresByMovieId, nativeQuery = true)
    // List<Genre> findGenresByMovieId(Long id);

    public List<Movie> findAllByOrderByMovieTitleAsc();

    public List<Movie> findAllByOrderByReleaseDateDesc();
    
}
