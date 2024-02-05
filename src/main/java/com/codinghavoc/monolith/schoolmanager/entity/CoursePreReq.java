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
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "course_pre_req", schema = "school_manager",
uniqueConstraints = {@UniqueConstraint(columnNames = {"course_id","prereq_id"})})
public class CoursePreReq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cpr_id")
    private Long cprId;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "prereq_id")
    private Long prereqId;
    
}
