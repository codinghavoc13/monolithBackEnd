package com.codinghavoc.monolith.schoolmanager.dto;

import java.util.List;

public class SMStudentDetailDTO {
    public List<SMCourseDetailDTO> enrolledCourses;
    public List<SMUserDTO> parents;
    public SMUserDTO student;
    //TODO will I want to add a list for grades
}