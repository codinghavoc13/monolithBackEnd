package com.codinghavoc.monolith.schoolmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codinghavoc.monolith.schoolmanager.dto.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.GradeEntry;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.service.TeacherSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private TeacherSvc teacherSvc;
    
    @GetMapping("/getAssignments/teacher_id/{teacher_id}")
    public ResponseEntity<List<Assignment>> getAssignmentsByTeacher(@PathVariable Long teacher_id){
        return new ResponseEntity<>(teacherSvc.getAssignmentsByTeacherId(teacher_id), HttpStatus.OK);
    }

    //will need another getAssignments EP with teacher and student id
    //TODO something along the lines of get grades by student and teacher ids
    // @GetMapping("/getAssignments/teacher_id/{teacher_id}/student_id/{student_id}")
    // public ResponseEntity<List<Assignment>> getAssignmentsByTeacher(@PathVariable Long teacher_id, @PathVariable Long student_id){
    //     return new ResponseEntity<>(teacherSvc.getAssignmentsByTeacherIdAndStudentId(teacher_id), HttpStatus.OK);
    // }

    @GetMapping("/getStudents/{teacher_id}")
    public ResponseEntity<List<User>>getStudentsAssignedToTeacher(@PathVariable Long teacher_id){
        return new ResponseEntity<>(teacherSvc.getStudentsAssignedToTeacher(teacher_id),HttpStatus.OK);
    }

    @PostMapping("/saveGrade")
    public ResponseEntity<GradeEntry> saveGrade(@RequestBody SMReqDTO dto){
        return new ResponseEntity<>(teacherSvc.saveGradeEntry(dto), HttpStatus.OK);
    }

    @PostMapping("/saveNewAssignment")//tested, works, Save New Assignment
    public ResponseEntity<List<Assignment>> saveNewAssignment(@RequestBody SMReqDTO dto){
        System.out.println("TC-sna-1");
        return new ResponseEntity<>(teacherSvc.saveAssignment(dto), HttpStatus.CREATED);
    }
}
