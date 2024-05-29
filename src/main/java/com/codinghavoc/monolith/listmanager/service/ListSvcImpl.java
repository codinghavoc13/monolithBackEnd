package com.codinghavoc.monolith.listmanager.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.listmanager.dto.ListInfoDto;
import com.codinghavoc.monolith.listmanager.dto.ListItemDto;
import com.codinghavoc.monolith.listmanager.entity.ListInfo;
import com.codinghavoc.monolith.listmanager.entity.ListItem;
import com.codinghavoc.monolith.listmanager.repo.ListInfoRepo;
import com.codinghavoc.monolith.listmanager.repo.ListItemRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ListSvcImpl implements ListSvc {
    private ListInfoRepo listInfoRepo;
    private ListItemRepo listItemRepo;

    @Override
    public List<ListInfoDto> getListsByUser(Long userId){
        ArrayList<ListInfoDto> result = new ArrayList<>();
        List<ListInfo> working = listInfoRepo.findByListDetailsByUserId(userId);
        ListInfoDto dto;
        List<ListItem> items;
        for(ListInfo ld : working){
            dto = new ListInfoDto(ld);
            // items = listItemRepo.findItemsByListId(ld.getListId());
            // for(ListItem li : items){
            //     dto.listItems.add(new ListItemDto(li));
            // }
            dto.numberOfItems = listItemRepo.countListItems(ld.getListId());
            result.add(dto);
        }
        return result;
    }

    @Override
    public ListInfoDto getListById(Long listId){
        if(listInfoRepo.checkForExistingListDetail(listId)==1){
            ListInfoDto result = new ListInfoDto(listInfoRepo.findListDetailsById(listId));
            List<ListItem> items = listItemRepo.findItemsByListId(listId);
            result.listItems = new ArrayList<>();
            for(ListItem item : items){
                result.listItems.add(new ListItemDto(item));
            }
            return result;
        } else {
            return new ListInfoDto();
        }
    }

    @Override
    public ListInfoDto updateList(ListInfoDto dto){
        ListInfo temp;
        ListItem tempItem;
        ListInfoDto result;
        if(dto.listId==0){ //adding a new list
            /*
            * if dto.list_id is 0:
            * - create a new ListDetail
            * - save to temp
            * - get the items in the dto
            * - update the list_id in the items
            * - save items to db
            * - create new LDD with updated info and add to result
            */
            result = new ListInfoDto();
            temp = listInfoRepo.save(new ListInfo(dto));
            result = new ListInfoDto(temp);
            result.listItems = new ArrayList<>();
            for(ListItemDto itemDto : dto.listItems){
                tempItem = new ListItem(itemDto);
                tempItem.setListId(result.listId);
                result.listItems.add(new ListItemDto(listItemRepo.save(tempItem)));
            }
            return result;
        } else { //updating an existing list
            /*
            * if dto.list_id is not 0:
            * - get list details by list_id
            * - compare db entry with dto.list_details, save/update if different
            * - add details to result
            * - for each list item in dto that has an id greater than 0, repeat
            *      retrieve/compare/save, add to result.list_items; if item id is 0, 
            *      save new LI to db and add to result.list_items
            */
            result = new ListInfoDto();
            return result;
        }
    }
}
