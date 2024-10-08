package com.codinghavoc.monolith.mmdb.exception;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(Long id){
        super("No movie with an id of " + id + " exists in the database");
    }    
}
