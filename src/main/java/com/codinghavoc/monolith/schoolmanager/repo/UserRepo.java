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

    static String qryUsernames = "select username from school_manager.users";
    @Query(value = qryUsernames, nativeQuery = true)
    List<String> getUserNames();

    static String qryGetStaffByUsername = """
        select * 
        from school_manager.users as s
        where s.username=?1
        """;
    @Query(value = qryGetStaffByUsername, nativeQuery = true)
    User getStaffByUsername(String username);

    static String qryGetUsersByLastNameAsc = """
        select * from school_manager.users as s order by s.last_name asc
        """;
    @Query(value = qryGetUsersByLastNameAsc, nativeQuery = true)
    List<User> getUsersByLastNameAsc();

    static String qryGetUsersByRoleLastNameAsc = """
        select * from school_manager.users as s where s.role=?1 order by s.last_name asc
        """;
    @Query(value = qryGetUsersByRoleLastNameAsc, nativeQuery = true)
    List<User> getUsersByRoleLastNameAsc(String role);

    static String qryGetStudentsByGradeLevel = """
            select * 
            from school_manager.users as u
            where u.grade_level=?1
            """;
    @Query(value = qryGetStudentsByGradeLevel, nativeQuery = true)
    List<User>getUsersByGradeLevel(String grade_level);

    static String qryGetStudentsNotAssignedToTeacher = """
        select u.*
        from school_manager.users as u
        where u.user_id not in 
        (select u.user_id 
         from school_manager.users as u 
         inner join school_manager.student_teacher as st on u.user_id=st.student_id)
        and u.role='STUDENT'
        order by u.last_name
                    """;
    @Query(value = qryGetStudentsNotAssignedToTeacher, nativeQuery = true)
    List<User> getStudentsNotAssignedToTeacher();

    static String qryGetUnverifiedUsers = """
        select * from school_manager.users as u
        where u.verified=false
        """;;
    @Query(value = qryGetUnverifiedUsers, nativeQuery = true)
    List<User>getUnverifiedUsers();

    static String qryUpdateUserVerified = """
        update school_manager.users set verified=true where user_id=?1
        """;
    @Query(value = qryUpdateUserVerified, nativeQuery = true)
    void verifyUser(Long user_id);
}
