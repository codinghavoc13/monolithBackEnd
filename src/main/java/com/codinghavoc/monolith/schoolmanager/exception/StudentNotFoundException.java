package com.codinghavoc.monolith.schoolmanager.exception;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(Long id){
        super("No student with an id of " + id + " exists in the database");
    }    
}
