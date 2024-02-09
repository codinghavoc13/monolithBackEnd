package com.codinghavoc.monolith.schoolmanager.dto;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.entity.Course;

public class SMStudentListDTO {
    public Course course;
    public int period;
    public List<SMUserDTO> students;
    
}
