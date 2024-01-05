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
@NoArgsConstructor
@Entity
@Table(name = "course_teacher", schema = "school_manager",uniqueConstraints = {@UniqueConstraint(columnNames = {"teacher_id", "course_id"})})
public class CourseTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ct_id")
    private Long ct_id;

    @NonNull
    @Column(name = "teacher_id")
    private Long teacher_id;

    @NonNull
    @Column(name = "course_id")
    private Long course_id;
    
}
