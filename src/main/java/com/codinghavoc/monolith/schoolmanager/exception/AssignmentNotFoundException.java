package com.codinghavoc.monolith.schoolmanager.exception;

public class AssignmentNotFoundException extends RuntimeException{
    public AssignmentNotFoundException(Long id){
        super("No assignment with an id of " + id + " exists in the database");
    }    
}
