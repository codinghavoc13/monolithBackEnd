package com.codinghavoc.monolith.listmanager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.listmanager.entity.ListItem;

public interface ListItemRepo extends CrudRepository<ListItem, Long>{
    static String qryFindItemsByListId = """
        select * from list_manager.list_items as li
        where li.list_id=?1
        order by li.order_position
        """;
    @Query(value=qryFindItemsByListId, nativeQuery = true)
    List<ListItem> findItemsByListId(Long id);
}
