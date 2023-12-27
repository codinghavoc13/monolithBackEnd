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

import com.codinghavoc.monolith.schoolmanager.dto.SMLoginDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.GradeEntry;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.service.UserSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/staff")
public class StaffController {
    private UserSvc userSvc;
    
    @GetMapping("/all")//tested, works, All Users
    public ResponseEntity<List<User>> getAllStaff(){
        return new ResponseEntity<>(userSvc.getAllStaff(),HttpStatus.OK);
    }

    //will likely need a series of new GET all mappings to get only students, admin, teachers, parents

    //will need another getAssignments EP with teacher and student id
    @GetMapping("/getAssignments/{teacher_id}")
    public ResponseEntity<List<Assignment>> getAssignmentsByTeacher(@PathVariable Long teacher_id){
        return new ResponseEntity<>(userSvc.getAssignmentsByTeacherId(teacher_id), HttpStatus.OK);
    }

    @GetMapping("/getStudents/{teacher_id}")
    public ResponseEntity<List<User>>getStudentsAssignedToTeacher(@PathVariable Long teacher_id){
        return new ResponseEntity<>(userSvc.getStudentsAssignedToTeacher(teacher_id),HttpStatus.OK);
    }

    //Login will need to be pulled out into it's own controller
    @PostMapping("/login")//tested, works, Staff Login Pass and Staff Login Pass
    public ResponseEntity<User> login(@RequestBody SMLoginDTO dto){
        return new ResponseEntity<>(userSvc.login(dto), HttpStatus.OK);
    }

    @PostMapping("/saveGrade")
    public ResponseEntity<GradeEntry> saveGrade(@RequestBody SMReqDTO dto){
        return new ResponseEntity<>(userSvc.saveGradeEntry(dto), HttpStatus.OK);
    }

    @PostMapping("/saveNewAssignment")//tested, works, Save New Assignment
    public ResponseEntity<List<Assignment>> saveNewAssignment(@RequestBody SMReqDTO dto){
        return new ResponseEntity<>(userSvc.saveAssignment(dto), HttpStatus.CREATED);
    }

    @PostMapping("/saveNewUsers") //tested, works, Save New Staff
    public ResponseEntity<List<User>> saveNewUsers(@RequestBody List<SMRegisterDTO> dtos){
        return new ResponseEntity<>(userSvc.saveUsers(dtos), HttpStatus.CREATED);
    }

    @PostMapping("/saveNewUser") //tested, works, Save New Students
    public ResponseEntity<User> saveNewUser(@RequestBody SMRegisterDTO dto){
        return new ResponseEntity<>(userSvc.saveUser(dto), HttpStatus.CREATED);
    }
}
