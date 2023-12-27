package com.codinghavoc.monolith.schoolmanager.service;

import com.codinghavoc.monolith.schoolmanager.entity.User;

public interface StaffSvc {
    User addStudentToTeacherRoster(Long teacher_id, Long student_id);
    //enroll new student
    //remove student from active roster
    //move student from one teacher to another
    // Student saveStudent(Student student);
}
