package com.codinghavoc.monolith.listmanager.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.listmanager.entity.ListDetails;
import java.util.List;


public interface ListDetailRepo extends CrudRepository<ListDetails,Long>{
    static String qryCheckForListDetails = """
        select count(*) from list_manager.list_details as ld
        where ld.list_id=?1
        """;
    @Query(value = qryCheckForListDetails, nativeQuery = true)
    Long checkForExistingListDetail(Long listId);

    static String qrySelectListDetailsByUserId = """
        select * from list_manager.list_details as ld
        where ld.user_id=?1
        """;
    @Query(value = qrySelectListDetailsByUserId, nativeQuery = true)
    List<ListDetails> findByListDetailsByUserId(Long userId);

    static String qrySelectListByListId = """
        select * from list_manager.list_details as ld
        where ld.list_id=?1
        """;
    /**
     * Get List Details information by list ID
     * @param listId
     * @return
     */
    @Query(value = qrySelectListByListId, nativeQuery = true)
    ListDetails findListDetailsById(Long listId);
}
