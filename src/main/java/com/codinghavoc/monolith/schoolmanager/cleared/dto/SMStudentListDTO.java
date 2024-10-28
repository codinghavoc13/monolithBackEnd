package com.codinghavoc.monolith.schoolmanager.cleared.dto;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.cleared.entity.Course;

public class SMStudentListDTO {
    public Course course;
    public Long cptId;
    public int period;
    public List<SMUserDTO> students;
    
}
