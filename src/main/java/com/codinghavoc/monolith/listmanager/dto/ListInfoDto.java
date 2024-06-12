package com.codinghavoc.monolith.listmanager.dto;

import java.util.ArrayList;
import java.util.List;

import com.codinghavoc.monolith.listmanager.entity.ListInfo;

public class ListInfoDto {
    public boolean ordered;
    /*
     * Look into creating sublists, lists within lists. Idea: a Gift
     * list with sub lists for different people, or a shopping list with
     * sublists for different stores.
     * 
     * Might want to enforce a limit of how many layers (main list-> 
     * sub-list 1 -> sub-list 2 -> sub-list n) mostly for display purposes. 
     * Each sub-list would be indented so from a mobile device POV would
     * need to account for limited screen width.
     * 
     * Possible workaround would be to not display sub-lists but instead display
     * buttons to view the sub-lists. Going with the workaround would allow 
     * unlimited sub-lists. Liking this idea as a solution
     * 
     * Would also need to apply a parentListId to point to the upper list
     */
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
        this.listItems = new ArrayList<>();
    }
}
