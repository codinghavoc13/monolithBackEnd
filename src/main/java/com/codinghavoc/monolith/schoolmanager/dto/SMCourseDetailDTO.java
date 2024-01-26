package com.codinghavoc.monolith.schoolmanager.dto;

import com.codinghavoc.monolith.schoolmanager.enums.CourseBlock;

public class SMCourseDetailDTO {
    //add the cptId
    public Long courseId;
    public String courseName;
    public CourseBlock courseBlock;
    public String teacherFirstName;
    public String teacherLastName;
    public Long teacherId;
    public int period;
    public double credit;
}
