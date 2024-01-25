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
    private Long courseId;

    @NonNull
    @Column(name = "course_name")
    private String courseName;

    @NonNull
    @Column(name = "course_block")
    @Enumerated(EnumType.STRING)
    private CourseBlock courseBlock;

    //this will be moved
    // @Column(name = "period")
    // private int period;

    @Column(name = "credit")
    private double credit;

    /*
     * In order to implement course prequisities, need to rework this class
     * - pull the period value out and place in a separate class
     * - new class would have a unique id, period, foriegn key back to here
     * - new entity object for course prereqs
     * 
     * - Course:
     *      courseId
     *      courseName
     *      courseBlock
     *      credit
     * - CourseSchedule *this may replace CourseTeacher
     *      **Update the CourseTeacher class to include period
     *      courseSchedId
     *      courseId
     *      teacherId
     *      period
     *      *unique constraint: courseId+teacherId+period (one teacher cannot teach the
     *          same course at the same time more than once)
     * - CoursePrerequisites
     *      crId
     *      courseId
     *      prereqId
     */
}
