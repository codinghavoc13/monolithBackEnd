package com.codinghavoc.monolith.schoolmanager.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.entity.Staff;

public interface StaffRepo extends CrudRepository<Staff,Long>{
    static String checkStudentTeacherEntry = """
            select count(*)
            from monolithdb.student_teacher
            where staff_id=?1 and student_id=?2
            """;
    @Query(value=checkStudentTeacherEntry, nativeQuery = true)
    Long checkForStudentTeacherEntry(Long staff_id, Long student_id);
}
