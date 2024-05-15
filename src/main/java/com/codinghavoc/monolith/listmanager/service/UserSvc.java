package com.codinghavoc.monolith.listmanager.service;

import java.util.List;

import com.codinghavoc.monolith.listmanager.dto.UserDto;
import com.codinghavoc.monolith.listmanager.entity.User;

public interface UserSvc {
    List<User> getAllUsers();
    User saveUser(UserDto dto);
}
