package com.codinghavoc.monolith.schoolmanager.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("No User with an id of " + id + " exists in the database");
    }    
}
