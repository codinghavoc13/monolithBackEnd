package com.codinghavoc.monolith.schoolmanager.dto;

import java.time.LocalDate;

public class SMIndividualGradeDTO {
    public String courseName;
    public String assignmentTitle;
    public String assignmentType;
    public LocalDate assignmentDueDate;
    public int period;
    public String studentFirstName;
    public String studentLastName;
    public Double grade;
    public Long gradeId;

    @Override
    public String toString() {
        return "SMGradeBookDTO [courseName=" + courseName + ", assignmentTitle=" + assignmentTitle + ", assignmentType="
                + assignmentType + ", assignmentDueDate=" + assignmentDueDate + ", period=" + period
                + ", studentFirstName=" + studentFirstName + ", studentLastName=" + studentLastName + ", grade=" + grade
                + "]";
    }
    
}
