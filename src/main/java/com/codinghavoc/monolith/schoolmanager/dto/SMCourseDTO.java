package com.codinghavoc.monolith.schoolmanager.dto;
import java.util.List;


/**
 * Send one of the following:
 * - A course_id and a set of student_ids
 * - A student_id and a set of course_ids
 */
public class SMCourseDTO {
    public Long courseId;
    public List<Long> courseIds;
    public Long cptId;
    public List<Long> cptIds;
    public int period;
    public Long studentId;
    public List<Long> studentIds;
    public Long teacherId;
    // @Override
    // public String toString() {
    //     return "SMCourseDTO [course_id=" + course_id + ", student_ids=" + student_ids + "]";
    // }
}
