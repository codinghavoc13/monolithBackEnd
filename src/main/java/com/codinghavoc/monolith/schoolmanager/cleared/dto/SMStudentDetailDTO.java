package com.codinghavoc.monolith.schoolmanager.cleared.dto;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.cleared.entity.GradeEntry;

public class SMStudentDetailDTO {
    public double creditCount;
    public List<SMCourseDetailDTO> enrolledCourses;
    public List<GradeEntry> gradeEntries;
    public List<SMUserDTO> parents;
    public SMUserDTO student;
}