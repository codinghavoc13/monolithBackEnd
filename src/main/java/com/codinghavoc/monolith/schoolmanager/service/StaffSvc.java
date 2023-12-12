package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.Staff;
import com.codinghavoc.monolith.schoolmanager.entity.Student;

public interface StaffSvc {
    String addStudentToTeacherRoster(Long teacher_id, Long student_id);
    // List<Assignment> getAssignmentsByTeacherId(Long id);
    Staff getStaffMember(Long id);
    List<Student> getStudentsAssignedToTeacher(Long id);
    List<Staff> getAllStaff();
    Assignment saveAssignment(Assignment assignment, Long teacher_id);
    Staff saveStaff(Staff staff);
    
    //List<Staff> getStaffByRole(String role);
    //remove student from teacher roster
}
