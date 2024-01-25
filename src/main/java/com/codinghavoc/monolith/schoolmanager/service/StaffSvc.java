package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.codinghavoc.monolith.schoolmanager.dto.SMCourseDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMCourseDetailDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMUserDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Course;
import com.codinghavoc.monolith.schoolmanager.entity.CourseStudent;
import com.codinghavoc.monolith.schoolmanager.entity.CoursePeriodTeacher;

public interface StaffSvc {
    Course addNewCourse(Course course);
    ResponseEntity<List<CourseStudent>> assignCoursesToStudent(SMCourseDTO dto);
    ResponseEntity<CourseStudent> assignStudentToCourse(SMCourseDTO dto);
    ResponseEntity<List<CourseStudent>> assignStudentsToCourse(SMCourseDTO dto);
    ResponseEntity<CoursePeriodTeacher> assignTeacherToCourse(SMCourseDTO dto);
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
