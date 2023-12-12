package com.codinghavoc.monolith.schoolmanager.exception;

public class StaffNotFoundException extends RuntimeException{
    public StaffNotFoundException(Long id){
        super("No staff member with an id of " + id + " exists in the database");
    }    
}
