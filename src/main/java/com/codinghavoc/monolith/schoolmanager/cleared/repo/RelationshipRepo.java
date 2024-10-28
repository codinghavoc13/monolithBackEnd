package com.codinghavoc.monolith.schoolmanager.cleared.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.cleared.entity.Relationship;

public interface RelationshipRepo extends CrudRepository<Relationship,Long>{
    
    static String qryGetRelativesByStudentId = """
            select * from sm.relationships as sm
            where sm.student_id=?1
            """;
    @Query(value=qryGetRelativesByStudentId, nativeQuery = true)
    public List<Relationship> getRelativesByStudentId(Long id);

    static String qryGetStudentsByParentId = """
            select * from school_manager.relationships as r
            where r.relative_id=?1
            """;
    @Query(value=qryGetStudentsByParentId, nativeQuery = true)
    public List<Relationship> getStudentsByParentId(Long id);
}
