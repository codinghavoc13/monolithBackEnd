package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.dto.SMLoginDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.GradeEntry;
import com.codinghavoc.monolith.schoolmanager.entity.User;

public interface UserSvc {
    User addStudentToTeacherRoster(Long teacher_id, Long student_id);
    List<User> getAllStaff();
    // List<User> getAllStudents();
    List<Assignment> getAssignmentsByTeacherId(Long id);
    User getStaffMember(Long id);
    List<String> getStaffUsernames();
    List<User> getStudentsAssignedToTeacher(Long id);
    // User getStudent(Long id);
    User login(SMLoginDTO dto);
    List<Assignment> saveAssignment(SMReqDTO dto);
    GradeEntry saveGradeEntry(SMReqDTO dto);
    User saveUser(SMRegisterDTO user);
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
