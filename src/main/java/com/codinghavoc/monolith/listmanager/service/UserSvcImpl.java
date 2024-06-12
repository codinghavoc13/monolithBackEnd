package com.codinghavoc.monolith.listmanager.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.listmanager.dto.UserDto;
import com.codinghavoc.monolith.listmanager.entity.User;
import com.codinghavoc.monolith.listmanager.repo.UserRepo;
import com.codinghavoc.monolith.util.PasswordHashUtil;

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
    public ResponseEntity<UserDto> login(UserDto dto){
        User check = userRepo.findUserByEmailAddress(dto.email);
        if(check!=null){
            if(PasswordHashUtil.validateWithPBKDF(dto.pwClear, check.getPasswordSalt(), check.getPasswordHash())){
                return new ResponseEntity<>(new UserDto(check),HttpStatus.OK);
            } else {
                //Email address is correct but password is incorrect
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            //No record found in the db with a matching email address
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public User saveUser(UserDto dto){
        return userRepo.save(new User(dto));
    }
}
