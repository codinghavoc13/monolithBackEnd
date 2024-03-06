package com.codinghavoc.monolith.schoolmanager.repo;

import java.util.List;

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
    
    static String qryFindByCPTIds = """
        select * from school_manager.grade_entry as ge
        where ge.cpt_id in ?1
        """;
    @Query(value = qryFindByCPTIds, nativeQuery = true)
    List<GradeEntry> findByCptIdIn(List<Long> cptIds);

    static String qryFindByStudentId = """
        select * from school_manager.grade_entry as ge where ge.student_id = ?1
        """;
    @Query(value = qryFindByStudentId, nativeQuery = true)
    List<GradeEntry> findByStudentId(Long studentId);

    static String qryFindByStudentIdCptId = """
        select * from school_manager.grade_entry as ge where ge.student_id = ?1 and ge.cpt_id = ?2
            """;
    @Query(value = qryFindByStudentIdCptId, nativeQuery = true)
    List<GradeEntry> findByStudentIdCptId(Long studentId, Long cptId);
}
