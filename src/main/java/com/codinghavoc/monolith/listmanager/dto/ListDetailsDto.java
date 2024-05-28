package com.codinghavoc.monolith.listmanager.dto;

import java.util.ArrayList;
import java.util.List;

import com.codinghavoc.monolith.listmanager.entity.ListDetails;

public class ListDetailsDto {
    public Long listId;
    public Long userId;
    public String listName;
    public String listDetails;
    public List<ListItemDto> listItems;

    public ListDetailsDto(){}

    public ListDetailsDto (ListDetails ld){
        this.listId = ld.getListId();
        this.userId = ld.getUserId();
        this.listDetails = ld.getListDetails();
        this.listName = ld.getListName();
        this.listItems = new ArrayList<>();
    }
}
