package com.codinghavoc.monolith.schoolmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.service.StaffSvc;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/staff")
public class StaffController {
    private StaffSvc staffSvc;

    @PostMapping("/saveNewUser") //tested, works, Save New Students
    public ResponseEntity<User> saveNewUser(@RequestBody SMRegisterDTO dto){
        return new ResponseEntity<>(staffSvc.saveUser(dto), HttpStatus.CREATED);
    }

    @GetMapping("/getStudentsNotAssignedToTeacher")
    public ResponseEntity<List<User>> getStudentsNotAssignedToTeacher(){
        return new ResponseEntity<List<User>>(staffSvc.getStudentsNotAssignedToTeacher(),HttpStatus.OK);
    }
}
