package com.codinghavoc.monolith.schoolmanager.dto;

import com.codinghavoc.monolith.schoolmanager.enums.CourseLength;

public class SMCourseDetailDTO {
    public Long courseId;
    public String courseName;
    public CourseLength courseLength;
    public String teacherFirstName;
    public String teacherLastName;
    public Long teacher_id;
    public int period;    
}
