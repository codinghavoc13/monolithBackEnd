package com.codinghavoc.monolith.schoolmanager.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.entity.GradeEntry;

public interface GradeEntryRepo extends CrudRepository<GradeEntry,Long>{
    static String qryFindByStudentAndAssignmentId = """
        select * from school_manager.grade_entry
        where student_id=?1 and assignment_id=?2
        """;
    @Query(value = qryFindByStudentAndAssignmentId, nativeQuery = true)
    public GradeEntry findByStudentAndAssignmentId(Long student_id, Long assignment_id);
    
}
