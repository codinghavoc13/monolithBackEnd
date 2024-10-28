package com.codinghavoc.monolith.schoolmanager.cleared.service;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.cleared.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.cleared.entity.Course;
import com.codinghavoc.monolith.schoolmanager.cleared.entity.User;

import com.codinghavoc.monolith.schoolmanager.cleared.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.cleared.entity.Course;
import com.codinghavoc.monolith.schoolmanager.cleared.entity.User;

public interface SUSvc {
    List<Course> getAllCourses();
    List<User> saveUsers(List<SMRegisterDTO> users);
}
