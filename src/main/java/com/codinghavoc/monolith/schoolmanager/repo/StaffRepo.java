package com.codinghavoc.monolith.schoolmanager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.entity.Staff;

public interface StaffRepo extends CrudRepository<Staff,Long>{
    static String checkStudentTeacherEntry = """
            select count(*)
            from school_manager.student_teacher
            where staff_id=?1 and student_id=?2
            """;
    @Query(value=checkStudentTeacherEntry, nativeQuery = true)
    Long checkForStudentTeacherEntry(Long staff_id, Long student_id);

    static String qryGetStaffUsernames = "select s.username from school_manager.staff";
    @Query(value = qryGetStaffUsernames, nativeQuery = true)
    List<String> getUserNames();

    static String qryGetStaffByUsername = """
            select * 
            from school_manager.staff as s
            where s.username=?1
            """;
    @Query(value = qryGetStaffByUsername, nativeQuery = true)
    Staff getStaffByUsername(String username);
}
