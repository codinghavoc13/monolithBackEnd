package com.codinghavoc.monolith.schoolmanager.entity;

import com.codinghavoc.monolith.schoolmanager.enums.CourseLength;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "course", schema = "school_manager")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_id")
    private Long course_id;

    @NonNull
    @Column(name = "course_name")
    private String courseName;

    @NonNull
    @Column(name = "course_length")
    @Enumerated(EnumType.STRING)
    private CourseLength courseLength;
    
}
