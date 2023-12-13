package com.codinghavoc.monolith.schoolmanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "grade_entry", uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id","assignment_id"})})
public class GradeEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Long grade_id;

    @NonNull
    @Column(name = "staff_id")
    private Long staff_id;

    @NonNull
    @Column(name = "student_id")
    private Long student_id;

    @NonNull
    @Column(name = "assignment_id")
    private Long assignment_id;

    // @NonNull
    @Column(name = "grade")
    private double grade;

    public GradeEntry(Long staff, Long student, Long assignment){
        this.staff_id = staff;
        this.student_id = student;
        this.assignment_id = assignment;
    }

    public GradeEntry(Long staff, Long student, Long assignment, double grade){
        this.staff_id = staff;
        this.student_id = student;
        this.assignment_id = assignment;
        this.grade = grade;
    }
}
