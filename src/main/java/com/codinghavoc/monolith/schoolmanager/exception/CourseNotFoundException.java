package com.codinghavoc.monolith.schoolmanager.exception;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(Long id){
        super("No course with an id of " + id + " exists in the database");
    }    
}
