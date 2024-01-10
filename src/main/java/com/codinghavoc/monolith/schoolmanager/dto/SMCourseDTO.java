package com.codinghavoc.monolith.schoolmanager.dto;
import java.util.List;

/**
 * Send one of the following:
 * - A course_id and a set of student_ids
 * - A student_id and a set of course_ids
 */
public class SMCourseDTO {
    public Long course_id;
    public Long student_id;
    public Long teacher_id;
    public List<Long> course_ids;
    public List<Long> student_ids;
    @Override
    public String toString() {
        return "SMCourseDTO [course_id=" + course_id + ", student_ids=" + student_ids + "]";
    }
}
