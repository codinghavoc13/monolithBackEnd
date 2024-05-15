package com.codinghavoc.monolith.listmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.listmanager.dto.UserDto;
import com.codinghavoc.monolith.listmanager.entity.User;
import com.codinghavoc.monolith.listmanager.repo.UserRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserSvcImpl implements UserSvc{
    private UserRepo userRepo;

    @Override
    public List<User> getAllUsers(){
        return (List<User>) userRepo.findAll();
    }

    @Override
    public User saveUser(UserDto dto){
        return userRepo.save(new User(dto));
    }
}
