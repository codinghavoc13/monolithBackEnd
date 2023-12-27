package com.codinghavoc.monolith.schoolmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codinghavoc.monolith.schoolmanager.dto.SMLoginDTO;
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
        return new ResponseEntity<>(userSvc.login(dto), HttpStatus.OK);
    }
    
}
