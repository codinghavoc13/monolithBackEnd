package com.codinghavoc.monolith.schoolmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.service.SUSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/su")
public class SUController {
    private SUSvc suSvc;

    @PostMapping("/saveNewUsers") //tested, works, Save New Staff
    public ResponseEntity<List<User>> saveNewUsers(@RequestBody List<SMRegisterDTO> dtos){
        return new ResponseEntity<>(suSvc.saveUsers(dtos), HttpStatus.CREATED);
    }
}
