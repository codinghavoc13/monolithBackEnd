package com.codinghavoc.monolith.listmanager.dto;

import java.util.ArrayList;
import java.util.List;

import com.codinghavoc.monolith.listmanager.entity.ListInfo;

public class ListInfoDto {
    public boolean ordered;
    public List<ListItemDto> listItems;
    public Long listId;
    public Long numberOfItems;
    public Long userId;
    public String listName;
    public String listNotes;

    public ListInfoDto(){}

    public ListInfoDto (ListInfo ld){
        this.listId = ld.getListId();
        this.userId = ld.getUserId();
        this.listNotes = ld.getListNotes();
        this.listName = ld.getListName();
        this.ordered = ld.getOrdered();
        // this.listItems = new ArrayList<>();
    }
}
