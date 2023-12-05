package com.codinghavoc.monolith.entity;

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
@Table(name="actors")
public class Actor {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long actor_id;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "imdb_link")
    private String imdbLink;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name="actor_movie",
        joinColumns = @JoinColumn(name="actor_id",referencedColumnName = "actor_id"),
        inverseJoinColumns = @JoinColumn(name="movie_id", referencedColumnName = "movie_id")
    )
    private Set<Movie>movies;
}