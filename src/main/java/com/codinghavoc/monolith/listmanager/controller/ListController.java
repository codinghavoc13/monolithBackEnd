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

import com.codinghavoc.monolith.listmanager.dto.ListInfoDto;
import com.codinghavoc.monolith.listmanager.dto.ListItemDto;
import com.codinghavoc.monolith.listmanager.service.ListSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/listManager/list")
public class ListController {
    private ListSvc svc;

    /*
     * Need a delete list method
     */

     @PostMapping("/deleteItem/{itemId}")
     public ResponseEntity<Boolean> deleteItem(@PathVariable Long itemId){
        return new ResponseEntity<>(svc.deleteListItem(itemId),HttpStatus.OK);
     }

     @GetMapping("/item/{itemId}")
     public ResponseEntity<ListItemDto> getItemByID(@PathVariable Long itemId){
        return new ResponseEntity<>(svc.getListItem(itemId),HttpStatus.OK);
     }

    /**
     * Get a list of lists by userId
     * @param userId The user's ID
     * @return
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ListInfoDto>> getListsByUserId(@PathVariable Long userId){
        return new ResponseEntity<>(svc.getListsByUser(userId),HttpStatus.OK);
    }

    /**
     * Retrieve a specific list
     * @param listId The list's ID
     * @return
     */
    @GetMapping("/list/{listId}")
    public ResponseEntity<ListInfoDto> getListByListId(@PathVariable Long listId){
        return new ResponseEntity<>(svc.getListById(listId),HttpStatus.OK);
    }

    @PostMapping("/updateItem")
    public ResponseEntity<ListItemDto> updateItem(@RequestBody ListItemDto dto){
        return new ResponseEntity<>(svc.updateItem(dto), HttpStatus.OK);
    }

    /**
     * Update a specific list
     * @param listDto The list to update
     * @return
     */
    @PostMapping("/updateList")
    public ResponseEntity<ListInfoDto> updateList(@RequestBody ListInfoDto listDto){
        /*
         * may modify this to build a response entity inside the svc that way I can send back
         * a different HttpStatus code
         */
        return new ResponseEntity<>(svc.updateList(listDto),HttpStatus.OK);
    }
}
