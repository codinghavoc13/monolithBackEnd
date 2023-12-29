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
import com.codinghavoc.monolith.schoolmanager.service.UserSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/staff")
public class StaffController {
    private UserSvc userSvc;
    
    @GetMapping("/all")//tested, works, All Users
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userSvc.getAllUsers(),HttpStatus.OK);
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
