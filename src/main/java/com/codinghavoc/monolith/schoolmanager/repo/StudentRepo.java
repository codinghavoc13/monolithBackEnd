package com.codinghavoc.monolith.schoolmanager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.entity.Student;

public interface StudentRepo extends CrudRepository<Student,Long>{
    static String getStudentsByTeacherIdQry = """
            select s.* from monolithdb.students as s 
            join monolithdb.student_teacher as st 
            on s.student_id=st.student_id 
            where st.staff_id=?1
            """;
    @Query(value=getStudentsByTeacherIdQry, nativeQuery=true)
    List<Student> getStudentsByTeacherId(Long teacher_id);
    
}
