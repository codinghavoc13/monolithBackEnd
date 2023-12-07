package com.codinghavoc.monolith.entity;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="movies")
public class Movie {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long movie_id;

    @NonNull
    @Column(name = "movie_title")
    private String movieTitle;

    @NonNull
    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "plot_summary")
    private String plotSummary;

    // @Column(name = "imdb_link")
    // private String imdbLink;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name="actor_movie",
        joinColumns = @JoinColumn(name="movie_id",referencedColumnName = "movie_id"),
        inverseJoinColumns = @JoinColumn(name="actor_id", referencedColumnName = "actor_id")
    )
    private Set<Actor>actors;

    // @JsonIgnore
    @ManyToMany
    @JoinTable(
        name="movie_genre",
        joinColumns = @JoinColumn(name="movie_id",referencedColumnName = "movie_id"),
        inverseJoinColumns = @JoinColumn(name="genre_id", referencedColumnName = "genre_id")
    )
    private Set<Genre>genre;
    
}
