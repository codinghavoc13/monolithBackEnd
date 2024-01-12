package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.dto.SMStudentDetailDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMUserDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMLoginDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Relationship;

public interface UserSvc {
    //Some of these will be moved to Staff, student, parent specific services
    //retain as a general user
    Relationship addRelationship(SMReqDTO dto);
    Boolean checkUsername(SMRegisterDTO check);
    SMUserDTO enrollStudent(SMReqDTO dto);
    // List<User> getAllUsers();
    List<SMUserDTO> getAllUsersSimple();
    // List<User> getAllUsersNoPW();
    List<SMUserDTO> getRelatives(Long student_id);
    List<SMStudentDetailDTO> getStudentDetails();
    SMStudentDetailDTO getStudentDetails(Long student_id);
    List<SMUserDTO> getStudentsByParentId(Long parent_id);
    SMUserDTO getUser(Long id);
    SMUserDTO getUserSimple(Long id);
    List<SMUserDTO> getUsersByRole(String role);
    List<String> getUserNames();
    SMUserDTO login(SMLoginDTO dto);
    SMUserDTO saveUser(SMRegisterDTO user);

    //TODO will need to implement the following at some point
    //updateRelationship
    //removeRelationship
    
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
