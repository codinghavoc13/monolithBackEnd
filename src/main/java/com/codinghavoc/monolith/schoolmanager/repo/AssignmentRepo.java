package com.codinghavoc.monolith.schoolmanager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.entity.Assignment;

public interface AssignmentRepo extends CrudRepository<Assignment,Long>{
    static String qryFindAllAssignmentsByStaffId = """
            select * from school_manager.assignment where staff_id=?1
            """;
    @Query(value=qryFindAllAssignmentsByStaffId, nativeQuery=true)
    List<Assignment> findAllAssignmentByStaffId(Long staff_id);
    
    // static String qryFindAllAssignmentsByStaffIdAndStudentId = """
    //         select * from school_manager.assignment where staff_id=?1
    //         """;
    // @Query(value=qryFindAllAssignmentsByStaffId, nativeQuery=true)
    // List<Assignment> findAllAssignmentByStaffId(Long staff_id);
    
}
