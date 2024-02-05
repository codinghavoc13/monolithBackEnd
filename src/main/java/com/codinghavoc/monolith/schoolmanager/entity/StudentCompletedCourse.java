package com.codinghavoc.monolith.schoolmanager.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="complete_course_student", schema = "school_manager",
uniqueConstraints = {@UniqueConstraint(columnNames = {"course_id","student_id"})})
public class StudentCompletedCourse{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ccs_id")
    private Long sccId;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "completed_date")
    private LocalDate completedDate;

    @Column(name = "final_grade")
    private double finalGrade;
}