package com.codinghavoc.monolith.schoolmanager.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codinghavoc.monolith.schoolmanager.dto.SMCourseDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMCourseDetailDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMUserDTO;
import com.codinghavoc.monolith.schoolmanager.entity.CourseStudent;
import com.codinghavoc.monolith.schoolmanager.entity.CoursePeriodTeacher;
import com.codinghavoc.monolith.schoolmanager.service.StaffSvc;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/staff")
public class StaffController {
    private StaffSvc staffSvc;

    @PostMapping("/assignTeacherToCourse")
    public ResponseEntity<CoursePeriodTeacher> assignTeacherToCourse(@RequestBody SMCourseDTO dto){
        return staffSvc.assignTeacherToCourse(dto);
    }

    @PostMapping("/assignStudentsToCourse")
    public ResponseEntity<List<CourseStudent>> assignStudentsToCourse(@RequestBody SMCourseDTO dto){
        return staffSvc.assignStudentsToCourse(dto);
    }

    @GetMapping("/getCourseDetails/{term}")
    public ResponseEntity<List<SMCourseDetailDTO>> getCourseDetails(@PathVariable String term){
        return new ResponseEntity<>(staffSvc.getCourseDetails(term),HttpStatus.OK);
    }

    @GetMapping("/getCoursesByStudent/{studentId}")
    public ResponseEntity<List<SMCourseDetailDTO>> getCoursesByStudent(@PathVariable Long studentId){
        return new ResponseEntity<>(staffSvc.getCoursesByStudent(studentId), HttpStatus.OK);
    }

    @GetMapping("/getStudentsByGradeLevel/{gradeLevel}")
    public ResponseEntity<List<SMUserDTO>> getStudentsByGradeLevel(@PathVariable String gradeLevel){
        return new ResponseEntity<>(staffSvc.getStudentsByGrade(gradeLevel), HttpStatus.OK);
    }

    @GetMapping("/getStudentsNotAssignedToTeacher")
    public ResponseEntity<List<SMUserDTO>> getStudentsNotAssignedToTeacher(){
        return new ResponseEntity<List<SMUserDTO>>(staffSvc.getStudentsNotAssignedToTeacher(),HttpStatus.OK);
    }

    @GetMapping("/getUnverifiedUsers")
    public ResponseEntity<List<SMUserDTO>> getUnverifiedUsers(){
        return new ResponseEntity<List<SMUserDTO>>(staffSvc.getUnverifiedUsers(), HttpStatus.OK);
    }

    @PutMapping("/verifyUser/{user_id}")
    public ResponseEntity<SMUserDTO> verifyUser(@PathVariable Long user_id){
        return new ResponseEntity<SMUserDTO>(staffSvc.updateUserVerification(user_id),HttpStatus.OK);
    }


    // ResponseEntity<List<CourseStudent>> assignCoursesToStudent(SMCourseDTO dto);
    // ResponseEntity<CourseStudent> assignStudentToCourse(SMCourseDTO dto);
    // ResponseEntity<List<CourseStudent>> assignStudentsToCourse(SMCourseDTO dto);
}
