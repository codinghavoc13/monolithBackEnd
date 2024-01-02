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
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.service.UserSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private UserSvc userSvc;

    //Login will need to be pulled out into it's own controller
    @PostMapping("/login")//tested, works, Staff Login Pass and Staff Login Pass
    public ResponseEntity<User> login(@RequestBody SMLoginDTO dto){
        User result = userSvc.login(dto);
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);    
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/checkUsername")
    public ResponseEntity<Boolean> checkUsername(@RequestBody SMRegisterDTO dto){
        // System.out.println("uc-cu-1");
        return new ResponseEntity<Boolean>(userSvc.checkUsername(dto),HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<List<User>>(userSvc.getAllUsers(), HttpStatus.OK);
    }
    
    @GetMapping("/getAllUsersNoPW")
    public ResponseEntity<List<User>> getAllUsersNoPW(){
        return new ResponseEntity<List<User>>(userSvc.getAllUsersNoPW(), HttpStatus.OK);
    }

    @GetMapping("/getUsersByRole/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role){
        return new ResponseEntity<List<User>>(userSvc.getUsersByRole(role), HttpStatus.OK);
    }
}
