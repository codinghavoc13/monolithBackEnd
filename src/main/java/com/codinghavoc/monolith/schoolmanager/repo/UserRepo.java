package com.codinghavoc.monolith.schoolmanager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.entity.User;

public interface UserRepo extends CrudRepository<User, Long>{
    static String getStudentsByTeacherIdQry = """
            select s.* from school_manager.users as s 
            join school_manager.student_teacher as st 
            on s.student_id=st.student_id 
            where st.staff_id=?1
            """;
    @Query(value=getStudentsByTeacherIdQry, nativeQuery=true)
    List<User> getStudentsByTeacherId(Long teacher_id);
    
    static String checkStudentTeacherEntry = """
            select count(*)
            from school_manager.student_teacher
            where staff_id=?1 and student_id=?2
            """;
    @Query(value=checkStudentTeacherEntry, nativeQuery = true)
    Long checkForStudentTeacherEntry(Long staff_id, Long student_id);

    static String qryGetStaffUsernames = "select s.username from school_manager.users";
    @Query(value = qryGetStaffUsernames, nativeQuery = true)
    List<String> getUserNames();

    static String qryGetStaffByUsername = """
            select * 
            from school_manager.users as s
            where s.username=?1
            """;
    @Query(value = qryGetStaffByUsername, nativeQuery = true)
    User getStaffByUsername(String username);
}
