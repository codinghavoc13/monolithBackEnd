package com.codinghavoc.monolith.schoolmanager.dto;

import java.util.List;

public class GradeBookSummaryDTO {
    //course information for the accordion
    public Long cptId;
    public String courseName;
    public String courseBlock;
    public Integer period;
    //list of student and average info
    public List<IndividualGradeSummaryDTO> studentInfo;
}
