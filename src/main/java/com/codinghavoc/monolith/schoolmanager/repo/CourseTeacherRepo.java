package com.codinghavoc.monolith.schoolmanager.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.entity.CourseTeacher;

public interface CourseTeacherRepo extends CrudRepository<CourseTeacher, Long>{
    static String qryFindByCourseTeacher = """
        select * 
        from school_manager.course_teacher as ct
        where ct.course_id=?1 and ct.teacher_id=?2
        """;
    @Query(value = qryFindByCourseTeacher, nativeQuery = true)
    public CourseTeacher findByCourseTeacher(Long course_id, Long teacher_id);
}
