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
@Table(name = "grade_entry", schema = "school_manager", uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id","assignment_id"})})
public class GradeEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Long gradeId;

    /*
     * update this to use cptId instead of having courseId and teacherId
     */

    @NonNull
    @Column(name = "course_id")
    private Long courseId; //this will be replaced with cptId

    @NonNull
    @Column(name = "student_id")
    private Long studentId;

    @NonNull
    @Column(name = "teacher_id")
    private Long teacherId; //this will be replaced with cptId

    @NonNull
    @Column(name = "assignment_id")
    private Long assignmentId;

    // @NonNull
    @Column(name = "grade")
    private double grade;

    public GradeEntry(Long course, Long student, Long teacher, Long assignment){
        this.courseId = course;
        this.studentId = student;
        this.assignmentId = assignment;
    }

    public GradeEntry(Long course, Long student, Long teacher, Long assignment, double grade){
        this.courseId = course;
        this.studentId = student;
        this.assignmentId = assignment;
        this.grade = grade;
    }
}
