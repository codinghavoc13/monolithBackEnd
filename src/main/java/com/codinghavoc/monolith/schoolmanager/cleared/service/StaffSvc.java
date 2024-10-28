package com.codinghavoc.monolith.schoolmanager.cleared.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.codinghavoc.monolith.schoolmanager.cleared.dto.SMCourseDTO;
import com.codinghavoc.monolith.schoolmanager.cleared.dto.SMCourseDetailDTO;
import com.codinghavoc.monolith.schoolmanager.cleared.dto.SMFullCourseDetailDTO;
// import com.codinghavoc.monolith.schoolmanager.cleared.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.cleared.dto.SMStudentDetailDTO;
import com.codinghavoc.monolith.schoolmanager.cleared.dto.SMUserDTO;
import com.codinghavoc.monolith.schoolmanager.cleared.entity.Course;
import com.codinghavoc.monolith.schoolmanager.cleared.entity.CourseStudent;
// import com.codinghavoc.monolith.schoolmanager.cleared.entity.User;
import com.codinghavoc.monolith.schoolmanager.cleared.entity.CoursePeriodTeacher;

public interface StaffSvc {
    Course addNewCourse(Course course);
    // ResponseEntity<List<CourseStudent>> assignCoursesToStudent(SMCourseDTO dto);
    // ResponseEntity<CourseStudent> assignStudentToCourse(SMCourseDTO dto);
    ResponseEntity<List<CourseStudent>> assignStudentsToCourse(SMCourseDTO dto);
    ResponseEntity<CoursePeriodTeacher> assignTeacherToCourse(SMCourseDTO dto);
    List<SMStudentDetailDTO> getAllMiddleHighStudents();
    List<SMCourseDetailDTO> getCoursesByStudent(Long studentId);
    List<SMCourseDetailDTO> getCourseDetails(String term);
    List<SMFullCourseDetailDTO> getFullCourseDetails();
    List<SMStudentDetailDTO> getStudentsByGrade(String gradeLevel);
    List<SMStudentDetailDTO> getStudentsNotAssignedToTeacher();
    List<SMUserDTO> getUnverifiedUsers();

    // ResponseEntity<CourseTeacher> revisedAssignTeacherToCourse(SMCourseDTO dto);

    SMUserDTO updateUserVerification(Long id);
    // User saveUser(SMRegisterDTO user);
    //enroll new student
    //remove student from active roster
    //move student from one teacher to another
    // Student saveStudent(Student student);
    // ResponseEntity<List<CourseStudent>> testAssign();
}
