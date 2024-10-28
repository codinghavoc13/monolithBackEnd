package com.codinghavoc.monolith.schoolmanager.cleared.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.schoolmanager.cleared.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.cleared.entity.Course;
import com.codinghavoc.monolith.schoolmanager.cleared.entity.User;
import com.codinghavoc.monolith.schoolmanager.cleared.repo.CourseRepo;
import com.codinghavoc.monolith.schoolmanager.cleared.repo.UserRepo;

import java.util.ArrayList;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SUSvcImpl implements SUSvc {
    UserRepo userRepo;
    CourseRepo courseRepo;

    @Override
    public List<Course> getAllCourses(){
        return (List<Course>)courseRepo.findAll();
    }

    @Override
    public List<User> saveUsers(List<SMRegisterDTO> users){
        List<User> result = new ArrayList<>();
        for(SMRegisterDTO dto : users){
            result.add(userRepo.save(new User(dto)));
        }
        return result;
    }
    
}
