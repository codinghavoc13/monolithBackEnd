package com.codinghavoc.monolith.exception;

public class ActorNotFoundException extends RuntimeException{
    public ActorNotFoundException(Long id){
        super("No actor with an id of " + id + " exists in the database");
    }    
}