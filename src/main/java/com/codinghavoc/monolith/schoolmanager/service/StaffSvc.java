package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;

// import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.entity.User;

public interface StaffSvc {
    User addStudentToTeacherRoster(Long teacher_id, Long student_id);
    // get
    List<User> getStudentsNotAssignedToTeacher();
    List<User> getUnverifiedUsers();
    User updateUserVerification(Long id);
    // User saveUser(SMRegisterDTO user);
    //enroll new student
    //remove student from active roster
    //move student from one teacher to another
    // Student saveStudent(Student student);
}
