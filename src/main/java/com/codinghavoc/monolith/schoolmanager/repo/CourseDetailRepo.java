package com.codinghavoc.monolith.schoolmanager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.entity.CourseDetail;

public interface CourseDetailRepo extends CrudRepository<CourseDetail, Long>{
    static String qryGetCourseDetails = """
        select c.course_id as course_id, t.first_name as first_name, t.last_name as last_name, c.course_name as course_name
        from school_manager.users as t
        inner join school_manager.course_teacher ct on t.user_id=ct.teacher_id
        inner join school_manager.course as c on c.course_id=ct.course_id
        order by c.course_name
        """;
    @Query(value = qryGetCourseDetails, nativeQuery = true)
    public List<CourseDetail> getCourseDetails();
}
