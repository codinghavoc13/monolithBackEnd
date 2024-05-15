package com.codinghavoc.monolith.listmanager.repo;

import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.listmanager.entity.User;

public interface UserRepo extends CrudRepository<User,Long>{
    
}
