package com.codinghavoc.monolith.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MMDBResponse {
    private Actor actor;
    private List<Actor> actors;
    private Movie movie;
    private List<Movie> movies;
    // private List<Genre> genres;   
}
