package com.codinghavoc.monolith.schoolmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/getStudentsNotAssignedToTeacher")
    public ResponseEntity<List<User>> getStudentsNotAssignedToTeacher(){
        return new ResponseEntity<List<User>>(staffSvc.getStudentsNotAssignedToTeacher(),HttpStatus.OK);
    }

    @GetMapping("/getUnverifiedUsers")
    public ResponseEntity<List<User>> getUnverifiedUsers(){
        return new ResponseEntity<List<User>>(staffSvc.getUnverifiedUsers(), HttpStatus.OK);
    }

    @PutMapping("/verifyUser/{user_id}")
    public ResponseEntity<User> verifyUser(@PathVariable Long user_id){
        return new ResponseEntity<User>(staffSvc.updateUserVerification(user_id),HttpStatus.OK);
    }
}
