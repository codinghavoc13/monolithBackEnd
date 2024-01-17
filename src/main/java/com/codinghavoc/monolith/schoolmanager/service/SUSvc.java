package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Course;
import com.codinghavoc.monolith.schoolmanager.entity.User;

public interface SUSvc {
    List<Course> getAllCourses();
    List<User> saveUsers(List<SMRegisterDTO> users);
}
