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
    public Boolean checkUsername(SMRegisterDTO check){
        // System.out.println("Looking for: " + check.username);
        // for(String s : getUserNames()){
        //     System.out.println(s);
        // }
        // if(getUserNames().contains(check.username)){
        //     System.out.println("u-svc-1");
        //     check.username = null;
        // } else {
        //     System.out.println("u-svc-2");
        // }
        return getUserNames().contains(check.username);
    }

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

    /*
     * Possibly rework this to return a response entity
     */
    @Override
    public User login(SMLoginDTO dto){
        User check = userRepo.getStaffByUsername(dto.username);
        System.out.println(check);
        if(check != null){
            boolean valid = PasswordHashUtil.validateWithPBKDF(dto.password, check.getPasswordSalt(), check.getPasswordHash());
            if(valid) {
                System.out.println("us-1");
                return check;
            } else {
                System.out.println("us-2");
                return null;
            }
        } else return null;
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
