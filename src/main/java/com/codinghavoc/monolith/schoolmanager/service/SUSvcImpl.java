package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.repo.UserRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SUSvcImpl implements SUSvc {
    UserRepo userRepo;

    @Override
    public List<User> saveUsers(List<SMRegisterDTO> users){
        List<User> result = new ArrayList<>();
        for(SMRegisterDTO dto : users){
            result.add(userRepo.save(new User(dto)));
        }
        return result;
    }
    
}
