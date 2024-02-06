package com.codinghavoc.monolith.schoolmanager.dto;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.entity.Course;

/*
 * May need to add period information to this as well
 */
public class SMStudentListDTO {
    public Course course;
    public int period;
    public List<SMUserDTO> students;
    
}
