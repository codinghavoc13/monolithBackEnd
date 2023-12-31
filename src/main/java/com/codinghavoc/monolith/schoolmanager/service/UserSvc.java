package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.dto.SMLoginDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.entity.User;

public interface UserSvc {
    //Some of these will be moved to Staff, student, parent specific services
    //retain as a general user
    Boolean checkUsername(SMRegisterDTO check);
    List<User> getAllUsers();
    User getUser(Long id);
    List<String> getUserNames();
    User login(SMLoginDTO dto);
    User saveUser(SMRegisterDTO user);

    //move to a superadmin
    //saving single user would be a registration change; saving multiple would be a super admin update
    List<User> saveUsers(List<SMRegisterDTO> users);
    
    //List<Staff> getStaffByRole(String role);
    /*
     * transfer student from one teacher to another
     * - would need to modify the request DTO to include a second staff ID to indicate which 
     * teacher to move them to
     * - would need to figure out how to handle grade entries
     */
    /*
     * remove student from teacher roster
     * - would need to check to make sure cascading would work
     * - front end will need serious checks to make sure the user really means to remove student
     */
}
