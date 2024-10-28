package com.codinghavoc.monolith.schoolmanager.cleared.exception;

public class GradeEntryNotFoundException extends RuntimeException{
    public GradeEntryNotFoundException(Long id){
        super("No grade entry with an id of " + id + " exists in the database");
    }
}
