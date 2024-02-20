package com.codinghavoc.monolith.schoolmanager.dto;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.entity.GradeEntry;

public class SMStudentDetailDTO {
    public double creditCount;
    public List<SMCourseDetailDTO> enrolledCourses;
    public List<GradeEntry> gradeEntries;
    public List<SMUserDTO> parents;
    public SMUserDTO student;
}