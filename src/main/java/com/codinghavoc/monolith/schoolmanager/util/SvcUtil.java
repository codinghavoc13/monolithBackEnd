package com.codinghavoc.monolith.schoolmanager.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.codinghavoc.monolith.schoolmanager.dto.SMUserDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.exception.AssignmentNotFoundException;
import com.codinghavoc.monolith.schoolmanager.exception.UserNotFoundException;

public class SvcUtil {
    public static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new UserNotFoundException(id);
    }

    public static Assignment unwrapAssignment(Optional<Assignment> entity, Long id){
        if(entity.isPresent()) return entity.get();
        else throw new AssignmentNotFoundException(id);
    } 

    //Using the SMUserDTO, won't need this
    // public static List<User> clearPWFromResults(List<User> in){
    //     List<User> result = new ArrayList<>();
    //     for(User u : in){
    //         result.add(clearPWFromResult(u));
    //     }
    //     return result;
    // }

    // public static User clearPWFromResult(User in){
    //     in.setPasswordHash("");
    //     in.setPasswordSalt("");
    //     return in;
    // }

    public static String padString(String input){
        int length = 9;
        if(input.length() >= length){
            return input;
        }
        StringBuilder sb = new StringBuilder();
        while(sb.length() < (length - input.length())){
            sb.append('0');
        }
        sb.append(input);
        return sb.toString();
    }
    

    public static List<SMUserDTO> convertListUsers(List<User> users){
        List<SMUserDTO> result = new ArrayList<>();
        for(User user : users){
            result.add(new SMUserDTO(user));
        }
        return result;
    }    
}
