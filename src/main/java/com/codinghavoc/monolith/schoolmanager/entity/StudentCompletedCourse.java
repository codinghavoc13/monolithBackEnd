package com.codinghavoc.monolith.schoolmanager.entity;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentCompletedCourse{
    private Long sccId;
    private Long studentId;
    private Long courseId;
    private LocalDate completedDate;
    private double finalGrade;
}