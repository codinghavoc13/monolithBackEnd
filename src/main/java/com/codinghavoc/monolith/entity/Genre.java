package com.codinghavoc.monolith.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
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
@Table(name="genre")
public class Genre {
    @Id
    @NonNull
    @Column(name = "genre_id")
    private Long genreId;
    
    @NonNull
    @Column(name="genre")
    private String genre;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name="movie_genre",
        joinColumns = @JoinColumn(name="genre_id",referencedColumnName = "genre_id"),
        inverseJoinColumns = @JoinColumn(name="movie_id", referencedColumnName = "movie_id")
    )
    private Set<Movie>movies;
}