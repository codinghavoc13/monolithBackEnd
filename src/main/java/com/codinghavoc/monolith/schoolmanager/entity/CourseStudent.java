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

/* This will stay */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "course_student", schema = "school_manager",uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id","course_id"})})
public class CourseStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="st_id")
    private Long st_id;

    @Override
    public String toString() {
        return "CourseStudent [st_id=" + st_id + ", student_id=" + student_id + ", course_id=" + course_id + "]";
    }

    @NonNull
    @Column(name = "student_id")
    private Long student_id;

    @NonNull
    @Column(name = "course_id")
    private Long course_id;
    
}
