package com.codinghavoc.monolith.listmanager.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.listmanager.entity.ListInfo;
import java.util.List;


public interface ListInfoRepo extends CrudRepository<ListInfo,Long>{
    static String qryCheckForListInfo = """
        select count(*) from list_manager.list_info as ld
        where ld.list_id=?1
        """;
    @Query(value = qryCheckForListInfo, nativeQuery = true)
    Long checkForExistingListInfo(Long listId);

    static String qrySelectListInfoByUserId = """
        select * from list_manager.list_info as ld
        where ld.user_id=?1
        """;
    @Query(value = qrySelectListInfoByUserId, nativeQuery = true)
    List<ListInfo> findByListInfoByUserId(Long userId);

    static String qrySelectListByListId = """
        select * from list_manager.list_info as ld
        where ld.list_id=?1
        """;
    /**
     * Get List Details information by list ID
     * @param listId
     * @return
     */
    @Query(value = qrySelectListByListId, nativeQuery = true)
    ListInfo findListInfoById(Long listId);
}
