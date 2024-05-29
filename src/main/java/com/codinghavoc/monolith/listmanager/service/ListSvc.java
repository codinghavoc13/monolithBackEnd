package com.codinghavoc.monolith.listmanager.service;

import java.util.List;

import com.codinghavoc.monolith.listmanager.dto.ListInfoDto;

public interface ListSvc {
    List<ListInfoDto> getListsByUser(Long userId);
    ListInfoDto getListById(Long listId);
    /*
     * Need an update method that takes in a new list
     * - Updating list would include: updating any fields in list details;
     *      updating any existing items still in the list, removing items from
     *      the db that are not in the new list, adding items that are in the
     *      new list but not in the db
     * - Front end will need to send new list details and items back with an Id
     *      of 0
     */
    ListInfoDto updateList(ListInfoDto dto);
}
