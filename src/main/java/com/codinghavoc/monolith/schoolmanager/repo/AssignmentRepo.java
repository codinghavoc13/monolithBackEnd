package com.codinghavoc.monolith.schoolmanager.repo;

import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.entity.Assignment;

public interface AssignmentRepo extends CrudRepository<Assignment,Long>{
    // SELECT * FROM monolithdb.assignment
    // where staff_id=2
    
}
