package com.codinghavoc.monolith.schoolmanager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.entity.CoursePeriodTeacher;

public interface CPTRepo extends CrudRepository<CoursePeriodTeacher, Long>{
    //TODO this may now be broken
    static String qryFindByCourseTeacher = """
        select * 
        from school_manager.course_period_teacher as ct
        where ct.course_id=?1 and ct.teacher_id=?2 and cpt.period=?3
        """;
    @Query(value = qryFindByCourseTeacher, nativeQuery = true)
    public CoursePeriodTeacher findByCourseTeacher(Long courseId, Long teacherId, int period);

    static String qryGetElementaryCourses = """
        select cpt.*
        from school_manager.course_period_teacher as cpt
        join school_manager.course as c on c.course_id=cpt.course_id
        where c.credit=-1
        """;
    @Query(value = qryGetElementaryCourses, nativeQuery = true)
    List<CoursePeriodTeacher>getElementaryCourses();

    static String qryGetMiddleHighCourses = """
        select cpt.*
        from school_manager.course_period_teacher as cpt
        join school_manager.course as c on c.course_id=cpt.course_id
        where c.credit>=0
        """;
    @Query(value = qryGetMiddleHighCourses, nativeQuery = true)
    List<CoursePeriodTeacher>getMiddleHighCourses();

    static String qryGetCoursesByStudent = """
        select cpt.*
        from school_manager.course_period_teacher as cpt
        join school_manager.course_student as cs on cpt.cpt_id=cs.cpt_id
        where cs.student_id=?1
        """;
    @Query(value = qryGetCoursesByStudent, nativeQuery = true)
    List<CoursePeriodTeacher>getCoursesByStudent(Long studentId);

    static String qryFindByTeacher = """
        select * from school_manager.course_period_teacher as cpt where cpt.teacher_id=?1
        """;
    @Query(value = qryFindByTeacher, nativeQuery = true)
    List<CoursePeriodTeacher> findByTeacher(Long teacherId);
}