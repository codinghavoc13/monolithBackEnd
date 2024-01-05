package com.codinghavoc.monolith.schoolmanager.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.entity.CourseStudent;
import com.codinghavoc.monolith.schoolmanager.entity.CourseTeacher;

public interface CourseStudentRepo extends CrudRepository<CourseStudent, Long>{
    static String qryFindByCourseStudent = """
        select * 
        from school_manager.course_student as ct
        where ct.course_id=?1 and ct.student_id=?2
        """;
    @Query(value = qryFindByCourseStudent, nativeQuery = true)
    public CourseStudent findByCourseStudent(Long course_id, Long student_id);
}
