package com.codinghavoc.monolith.schoolmanager.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.entity.ConfigEntry;

public interface ConfigRepo extends CrudRepository<ConfigEntry,Long>{
    static String qryGetSchoolIdPrefix = """
        select * from school_manager.config as c where c.key='school_id_prefix'
        """;
    @Query(value = qryGetSchoolIdPrefix, nativeQuery=true)
    ConfigEntry getSchoolIdPrefix();

    static String qryGetSchoolIdCounter = """
        select * from school_manager.config as c where c.key='school_id_counter'
        """;
    @Query(value = qryGetSchoolIdCounter, nativeQuery=true)
    ConfigEntry getSchoolIdCounter();

    // static String qryUpdateSchoolIdCounter = """
    //     UPDATE school_manager.config
	//     SET value=?1
	//     WHERE key='school_id_counter'
    //     """;
    // @Query(value=qryUpdateSchoolIdCounter, nativeQuery = true)
    // void updateSchoolIdCounter(String newValue);
}
