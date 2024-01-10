package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.codinghavoc.monolith.schoolmanager.dto.SMCourseDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMCourseRespDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Course;
import com.codinghavoc.monolith.schoolmanager.entity.CourseDetail;
import com.codinghavoc.monolith.schoolmanager.entity.CourseStudent;
import com.codinghavoc.monolith.schoolmanager.entity.CourseTeacher;
import com.codinghavoc.monolith.schoolmanager.entity.User;

public interface StaffSvc {
    Course addNewCourse(Course course);
    ResponseEntity<List<CourseStudent>> assignCoursesToStudent(SMCourseDTO dto);
    ResponseEntity<CourseStudent> assignStudentToCourse(SMCourseDTO dto);
    ResponseEntity<List<CourseStudent>> assignStudentsToCourse(SMCourseDTO dto);
    ResponseEntity<CourseTeacher> assignTeacherToCourse(SMCourseDTO dto);
    List<CourseDetail> getCourseDetails();
    List<User> getStudentsNotAssignedToTeacher();
    List<User> getUnverifiedUsers();

    // ResponseEntity<CourseTeacher> revisedAssignTeacherToCourse(SMCourseDTO dto);

    User updateUserVerification(Long id);
    // User saveUser(SMRegisterDTO user);
    //enroll new student
    //remove student from active roster
    //move student from one teacher to another
    // Student saveStudent(Student student);
}
