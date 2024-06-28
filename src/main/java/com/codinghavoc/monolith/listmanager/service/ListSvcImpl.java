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
    public boolean deleteList(Long listId){
        if(listInfoRepo.findListInfoById(listId) != null){
            listInfoRepo.deleteById(listId);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteListItem(Long listItemId){
        if(listItemRepo.findListItemById(listItemId)!=null){
            listItemRepo.deleteById(listItemId);
            return true;
        }
        return false;
    }

    @Override
    public ListItemDto getListItem(Long itemId){
        ListItem item = listItemRepo.findListItemById(itemId);
        if(item == null){
            return new ListItemDto(-1L);
        } else {
            return new ListItemDto(item);
        }
    }

    @Override
    public List<ListInfoDto> getListsByUser(Long userId){
        ArrayList<ListInfoDto> result = new ArrayList<>();
        List<ListInfo> working = listInfoRepo.findByListInfoByUserId(userId);
        ListInfoDto dto;
        // List<ListItem> items;
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
        if(listInfoRepo.checkForExistingListInfo(listId)==1){
            ListInfoDto result = new ListInfoDto(listInfoRepo.findListInfoById(listId));
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
    public ListItemDto updateItem(ListItemDto dto){
        ListItem orig = listItemRepo.findListItemById(dto.listItemId);
        orig.updateOrig(new ListItem(dto));
        listItemRepo.save(orig);
        return new ListItemDto(orig);
    }

    /*
     * Need to relook this section, most of the code to bulk update all of the list
     * items is now null
     */
    @Override
    public ListInfoDto updateList(ListInfoDto dto){
        ListInfo temp;
        ListInfoDto result;
        if(dto.listId==-1){ //adding a new list
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
            ListInfo orig = listInfoRepo.findListInfoById(dto.listId);
            ListInfo newInfo = new ListInfo(dto);
            //if the new info is not the same as the original, update
            if(!orig.equals(newInfo)){
                //the two are not the same, update
                //yes, the equals is not comparing id
                orig.updateOrigList(newInfo);
                listInfoRepo.save(orig);
                result = new ListInfoDto(newInfo);
            } else {
                result = new ListInfoDto(orig);
            }
            //With new edit item popup, this whole section become moot
            //if there are items in the incoming list
            // if(dto.listItems.size()>0){
            //     ListItem origItem;
            //     ListItem newItem;
            //     for(ListItemDto item : dto.listItems){
            //         System.out.println(item.listItemId);
            //         //if the item has a listitemid of -1, it is a new item, go directly to save it
            //         if(item.listItemId == 0){
            //             listItemRepo.save(new ListItem(item));
            //             result.listItems.add(item);
            //         } else {
            //             //if the item has an id is not -1, it is an existing item
            //             //get the original item
            //             origItem = listItemRepo.findListItemById(item.listItemId);
            //             //build out the new item
            //             newItem = new ListItem(item);
            //             //compare; if the new is not the same as the original, update
            //             if(!origItem.equals(newItem)){
            //                 origItem.updateOrig(newItem);
            //                 listItemRepo.save(origItem);
            //                 result.listItems.add(new ListItemDto(origItem));
            //             } else {
            //                 result.listItems.add(new ListItemDto(origItem));
            //             }
            //         }
            //     }
            // }
            // result = new ListInfoDto();
            return result;
        }
    }
}
