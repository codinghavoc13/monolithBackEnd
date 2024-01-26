package com.codinghavoc.monolith.schoolmanager.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.entity.CourseStudent;

public interface CourseStudentRepo extends CrudRepository<CourseStudent, Long>{
    static String qryFindByCourseStudent = """
        select * 
        from school_manager.course_student as ct
        where ct.student_id=?1 and ct.cpt_id=?2
        """;
    @Query(value = qryFindByCourseStudent, nativeQuery = true)
    public CourseStudent findByCourseStudent(Long studentId, Long cptId);
}
