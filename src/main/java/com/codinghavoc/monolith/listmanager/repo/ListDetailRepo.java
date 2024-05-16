package com.codinghavoc.monolith.listmanager.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.listmanager.entity.ListDetails;
import java.util.List;


public interface ListDetailRepo extends CrudRepository<ListDetails,Long>{
    static String qrySelectListDetailsByUserId = """
        select * from list_manager.list_details as ld
        where ld.user_id=?1
        """;
    @Query(value = qrySelectListDetailsByUserId, nativeQuery = true)
    List<ListDetails> findByListDetailsByUserId(Long userId);
}
