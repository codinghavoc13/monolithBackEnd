package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.codinghavoc.monolith.schoolmanager.dto.SMCourseDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMCourseDetailDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMUserDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Course;
import com.codinghavoc.monolith.schoolmanager.entity.CourseStudentTeacher;
import com.codinghavoc.monolith.schoolmanager.entity.CourseTeacher;

public interface StaffSvc {
    Course addNewCourse(Course course);
    ResponseEntity<List<CourseStudentTeacher>> assignCoursesToStudent(SMCourseDTO dto);
    ResponseEntity<CourseStudentTeacher> assignStudentToCourse(SMCourseDTO dto);
    ResponseEntity<List<CourseStudentTeacher>> assignStudentsToCourse(SMCourseDTO dto);
    ResponseEntity<CourseTeacher> assignTeacherToCourse(SMCourseDTO dto);
    List<SMCourseDetailDTO> getCourseDetails(String term);
    List<SMUserDTO> getStudentsByGrade(String gradeLevel);
    List<SMUserDTO> getStudentsNotAssignedToTeacher();
    List<SMUserDTO> getUnverifiedUsers();

    // ResponseEntity<CourseTeacher> revisedAssignTeacherToCourse(SMCourseDTO dto);

    SMUserDTO updateUserVerification(Long id);
    // User saveUser(SMRegisterDTO user);
    //enroll new student
    //remove student from active roster
    //move student from one teacher to another
    // Student saveStudent(Student student);
}
