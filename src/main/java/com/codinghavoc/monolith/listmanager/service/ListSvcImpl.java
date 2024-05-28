package com.codinghavoc.monolith.listmanager.service;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.annotations.DialectOverride.OverridesAnnotation;
import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.listmanager.dto.ListDetailsDto;
import com.codinghavoc.monolith.listmanager.dto.ListItemDto;
import com.codinghavoc.monolith.listmanager.entity.ListDetails;
import com.codinghavoc.monolith.listmanager.entity.ListItem;
import com.codinghavoc.monolith.listmanager.repo.ListDetailRepo;
import com.codinghavoc.monolith.listmanager.repo.ListItemRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ListSvcImpl implements ListSvc {
    private ListDetailRepo listDetailRepo;
    private ListItemRepo listItemRepo;

    @Override
    public List<ListDetailsDto> getListsByUser(Long userId){
        ArrayList<ListDetailsDto> result = new ArrayList<>();
        List<ListDetails> working = listDetailRepo.findByListDetailsByUserId(userId);
        ListDetailsDto dto;
        List<ListItem> items;
        for(ListDetails ld : working){
            dto = new ListDetailsDto(ld);
            items = listItemRepo.findItemsByListId(ld.getListId());
            for(ListItem li : items){
                dto.listItems.add(new ListItemDto(li));
            }
            result.add(dto);
        }
        return result;
    }

    @Override
    public ListDetailsDto getListById(Long listId){
        if(listDetailRepo.checkForExistingListDetail(listId)==1){
            ListDetailsDto result = new ListDetailsDto(listDetailRepo.findListDetailsById(listId));
            List<ListItem> items = listItemRepo.findItemsByListId(listId);
            for(ListItem item : items){
                result.listItems.add(new ListItemDto(item));
            }
            return result;
        } else {
            return new ListDetailsDto();
        }
    }

    @Override
    public ListDetailsDto updateList(ListDetailsDto dto){
        ListDetails temp;
        ListItem tempItem;
        ListDetailsDto result;
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
            result = new ListDetailsDto();
            temp = listDetailRepo.save(new ListDetails(dto));
            result = new ListDetailsDto(temp);
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
            result = new ListDetailsDto();
            return result;
        }
    }
}
