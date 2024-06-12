package com.codinghavoc.monolith.listmanager.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.codinghavoc.monolith.listmanager.dto.UserDto;
import com.codinghavoc.monolith.listmanager.entity.User;

public interface UserSvc {
    List<User> getAllUsers();
    ResponseEntity<UserDto> login(UserDto dto);
    User saveUser(UserDto dto);
    //need
    //login
}
