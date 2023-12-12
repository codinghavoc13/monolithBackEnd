package com.codinghavoc.monolith.schoolmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.Staff;
import com.codinghavoc.monolith.schoolmanager.entity.Student;
import com.codinghavoc.monolith.schoolmanager.service.StaffSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/staff")
public class StaffController {
    private StaffSvc staffSvc;
    
    @GetMapping("/all")
    public ResponseEntity<List<Staff>> getAllStaff(){
        return new ResponseEntity<>(staffSvc.getAllStaff(),HttpStatus.OK);
    }

    @GetMapping("/getStudents/{teacher_id}")
    public ResponseEntity<List<Student>>getStudentsAssignedToTeacher(@PathVariable Long teacher_id){
        return new ResponseEntity<>(staffSvc.getStudentsAssignedToTeacher(teacher_id),HttpStatus.OK);
    }

    // @GetMapping("/getAssignments/{teacher_id}")
    // public ResponseEntity<List<Assignment>> getAssignmentsByTeacher(@PathVariable Long teacher_id){
    //     return new ResponseEntity<>(staffSvc.)
    // }
}
