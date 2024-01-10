package com.codinghavoc.monolith.schoolmanager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.entity.CourseDetail;
import com.codinghavoc.monolith.schoolmanager.entity.CourseTeacher;

public interface CourseTeacherRepo extends CrudRepository<CourseTeacher, Long>{
    static String qryFindByCourseTeacher = """
        select * 
        from school_manager.course_teacher as ct
        where ct.course_id=?1 and ct.teacher_id=?2
        """;
    @Query(value = qryFindByCourseTeacher, nativeQuery = true)
    public CourseTeacher findByCourseTeacher(Long course_id, Long teacher_id);

    // static String qryGetCourseDetails = """
    //     select c.course_id as course_id, t.first_name as first_name, t.last_name as last_name, c.course_name as course_name
    //     from school_manager.users as t
    //     inner join school_manager.course_teacher ct on t.user_id=ct.teacher_id
    //     inner join school_manager.course as c on c.course_id=ct.course_id
    //     order by c.course_name
    //     """;
    // @Query(value = qryGetCourseDetails, nativeQuery = true)
    // public List<CourseDetail> getCourseDetails();
}

// public Long course_id;
//     public String first_name;
//     public String last_name;
//     public String course_name;  