package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.entity.User;

public interface SUSvc {
    //move to a superadmin
    //saving single user would be a registration change; saving multiple would be a super admin update
    List<User> saveUsers(List<SMRegisterDTO> users);
}
