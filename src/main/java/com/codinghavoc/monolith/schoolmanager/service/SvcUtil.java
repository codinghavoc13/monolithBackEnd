package com.codinghavoc.monolith.schoolmanager.service;

import java.util.Optional;

import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.exception.AssignmentNotFoundException;
import com.codinghavoc.monolith.schoolmanager.exception.UserNotFoundException;

public class SvcUtil {

    static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new UserNotFoundException(id);
    }

    static Assignment unwrapAssignment(Optional<Assignment> entity, Long id){
        if(entity.isPresent()) return entity.get();
        else throw new AssignmentNotFoundException(id);
    } 
    
}
