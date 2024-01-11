package com.codinghavoc.monolith.schoolmanager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.entity.Course;

public interface CourseRepo extends CrudRepository<Course,Long>{

    static String qryGetCoursesByStudentId = """
        select c.* 
        from school_manager.course as c
        inner join school_manager.course_student as cs on c.course_id=cs.course_id
        inner join school_manager.users as u on u.user_id=cs.student_id
        where u.user_id=?1
        """;
    @Query(value = qryGetCoursesByStudentId, nativeQuery = true)
    public List<Course> getCoursesAssignedToStudent(Long student_id);
}
