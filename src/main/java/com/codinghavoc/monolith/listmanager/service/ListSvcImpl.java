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
            dto = new ListDetailsDto();
            dto.listId = ld.getListId();
            dto.listDetails = ld.getListDetails();
            dto.listName = ld.getListName();
            dto.listItems = new ArrayList<>();
            items = listItemRepo.findItemsByListId(ld.getListId());
            for(ListItem li : items){
                dto.listItems.add(new ListItemDto(li));
            }
            result.add(dto);
        }
        return result;
    }

    @Override
    public List<ListDetailsDto> updateLists(List<ListDetailsDto> dtos){
        ArrayList<ListDetailsDto> result = new ArrayList<>();
        for(ListDetailsDto dto : dtos){
            /*
             * if dto.list_id is 0:
             * - create a new ListDetail
             * - save to temp
             * - get the items in the dto
             * - update the list_id in the items
             * - save items to db
             * - create new LDD with updated info and add to result
             */
            /*
             * if dto.list_id is not 0:
             * - get list details by list_id
             * - compare db entry with dto.list_details, save/update if different
             * - add details to result
             * - for each list item in dto that has an id greater than 0, repeat
             *      retrieve/compare/save, add to result.list_items; if item id is 0, 
             *      save new LI to db and add to result.list_items
             */
        }
        return result;
    }
}
