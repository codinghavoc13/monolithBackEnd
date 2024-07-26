package com.codinghavoc.monolith.schoolmanager.entity;

import com.codinghavoc.monolith.schoolmanager.enums.CourseBlock;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "course_period_teacher", schema = "school_manager",uniqueConstraints = {@UniqueConstraint(columnNames = {"teacher_id", "course_id","period"})})
public class CoursePeriodTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cpt_id")
    private Long cptId;

    @NonNull
    @Column(name = "teacher_id")
    private Long teacherId;

    @NonNull
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "period")
    private int period;

    /**
     * Default to FULL_YEAR. Code will set to FALL_SEMESTER or SPRING_SEMESTER
     * based on user input
     */
    @NonNull
    @Column(name = "course_block")
    @Enumerated(EnumType.STRING)
    private CourseBlock courseBlock = CourseBlock.FULL_YEAR;
}
