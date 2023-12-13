package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;

// import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.entity.SMRespDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Staff;
import com.codinghavoc.monolith.schoolmanager.entity.Student;

public interface StaffSvc {
    SMRespDTO addStudentToTeacherRoster(Long teacher_id, Long student_id);
    SMRespDTO getAllStaff();
    // List<Student> getAllStudents();
    SMRespDTO getAssignmentsByTeacherId(Long id);
    SMRespDTO getStaffMember(Long id);
    SMRespDTO getStudentsAssignedToTeacher(Long id);
    // Student getStudent(Long id);
    SMRespDTO saveAssignment(SMReqDTO dto);
    SMRespDTO saveGradeEntry(SMReqDTO dto);
    SMRespDTO saveStaff(Staff staff);
    SMRespDTO saveStudent(List<Student> student);
    
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
