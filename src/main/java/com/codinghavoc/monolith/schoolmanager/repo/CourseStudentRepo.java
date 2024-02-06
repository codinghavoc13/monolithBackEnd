package com.codinghavoc.monolith.schoolmanager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.entity.CourseStudent;

public interface CourseStudentRepo extends CrudRepository<CourseStudent, Long>{
    // static String qryFindByCourseStudent = """
    //     select * 
    //     from school_manager.course_student as ct
    //     where ct.student_id=?1 and ct.cpt_id=?2
    //     """;
    // @Query(value = qryFindByCourseStudent, nativeQuery = true)
    // public CourseStudent findByCourseStudent(Long studentId, Long cptId);

    static String qryFindByStudentId = """
        select * from school_manager.course_student as cs where cs.student_id=?1
        """;
    @Query(value = qryFindByStudentId, nativeQuery=true)
    public List<CourseStudent> findByStudentId(Long studentId);

    static String qryFindStudentsByCPT = """
        select * from school_manager.course_student as cs 
        where cs.cpt_id=?1
            """;
    @Query(value = qryFindStudentsByCPT, nativeQuery = true)
    public List<CourseStudent> findStudentsByCPT(Long cptId);
}
