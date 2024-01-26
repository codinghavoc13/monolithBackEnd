package com.codinghavoc.monolith.schoolmanager.dto;
import java.util.List;

import com.codinghavoc.monolith.schoolmanager.entity.CoursePeriodTeacher;

/**
 * Send one of the following:
 * - A course_id and a set of student_ids
 * - A student_id and a set of course_ids
 */
public class SMCourseDTO {
    public Long courseId;
    public Long studentId;
    public Long teacherId;
    public int period;
    public List<Long> courseIds;
    public List<Long> studentIds;
    public Long cptId;
    // @Override
    // public String toString() {
    //     return "SMCourseDTO [course_id=" + course_id + ", student_ids=" + student_ids + "]";
    // }
}
