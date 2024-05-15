package com.codinghavoc.monolith.listmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codinghavoc.monolith.listmanager.dto.UserDto;
import com.codinghavoc.monolith.listmanager.entity.User;
import com.codinghavoc.monolith.listmanager.service.UserSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/listManager/user")
public class UserController {
    private UserSvc svc;

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return this.svc.getAllUsers();
    }

    @PostMapping("/newUser")
    public ResponseEntity<User> newUser(@RequestBody UserDto dto){
        return new ResponseEntity<>(svc.saveUser(dto),HttpStatus.OK);
    }
}
