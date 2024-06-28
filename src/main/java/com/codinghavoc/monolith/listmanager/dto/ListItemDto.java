package com.codinghavoc.monolith.listmanager.dto;

import com.codinghavoc.monolith.listmanager.entity.ListItem;

public class ListItemDto {
    public Long listItemId;
    public Long listId;
    public String itemName;
    // public Long quantity;
    public Long orderPosition;
    public String itemNotes;

    public ListItemDto(){}

    public ListItemDto(Long blankId){
        this.listItemId = blankId;
    }

    public ListItemDto(ListItem li){
        listItemId = li.getListItemId();
        listId = li.getListId();
        itemName = li.getItemName();
        // quantity = li.getQuantity();
        orderPosition = li.getOrderPosition();
        itemNotes = li.getItemNotes();
    }
}
