package com.codinghavoc.monolith.listmanager.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.listmanager.entity.User;

public interface UserRepo extends CrudRepository<User,Long>{
    static String qryGetUserByEmailAddress = """
        SELECT * FROM list_manager.users as u
        where u.email_address=?1
        """;
    @Query(value = qryGetUserByEmailAddress, nativeQuery = true)
    User findUserByEmailAddress(String email);
    
}
