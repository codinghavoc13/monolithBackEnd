package com.codinghavoc.monolith.listmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codinghavoc.monolith.listmanager.dto.ListDetailsDto;
import com.codinghavoc.monolith.listmanager.service.ListSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/listManager/list")
public class ListController {
    private ListSvc svc;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ListDetailsDto>> getListByUserId(@PathVariable Long userId){
        return new ResponseEntity<>(svc.getListsByUser(userId),HttpStatus.OK);
    }

    @GetMapping("/list/{listId}")
    public ResponseEntity<ListDetailsDto> getListByListId(@PathVariable Long listId){
        return new ResponseEntity<>(svc.getListById(listId),HttpStatus.OK);
    }

    //updateList
    @PostMapping("/updateList")
    public ResponseEntity<ListDetailsDto> updateList(@RequestBody ListDetailsDto listDto){
        return new ResponseEntity<>(svc.updateList(listDto),HttpStatus.OK);
    }
}
