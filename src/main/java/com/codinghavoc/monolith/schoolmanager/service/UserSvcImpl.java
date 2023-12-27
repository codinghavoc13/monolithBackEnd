package com.codinghavoc.monolith.schoolmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.schoolmanager.dto.SMLoginDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.repo.AssignmentRepo;
import com.codinghavoc.monolith.schoolmanager.repo.GradeEntryRepo;
import com.codinghavoc.monolith.schoolmanager.repo.UserRepo;
import com.codinghavoc.monolith.schoolmanager.util.PasswordHashUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserSvcImpl implements UserSvc{
    AssignmentRepo assignmentRepo;
    GradeEntryRepo geRepo;
    UserRepo userRepo;

    @Override
    public List<User> getAllUsers() {
        return (List<User>)userRepo.findAll();
    }

    @Override
    public User getUser(Long id) {
        return SvcUtil.unwrapUser(userRepo.findById(id),id);
    }
    
    @Override
    public List<String> getUserNames(){
        return (List<String>)userRepo.getUserNames();
    }

    @Override
    public User login(SMLoginDTO dto){
        User check = userRepo.getStaffByUsername(dto.username);
        boolean valid = PasswordHashUtil.validateWithPBKDF(dto.password, check.getPasswordSalt(), check.getPasswordHash());
        if(valid) {
            return check;
        } else {
            return null;
        }
    }

    @Override
    public User saveUser(SMRegisterDTO dto){
        return userRepo.save(new User(dto));
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
