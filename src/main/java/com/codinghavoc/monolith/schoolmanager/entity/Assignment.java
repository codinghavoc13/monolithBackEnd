package com.codinghavoc.monolith.schoolmanager.entity;

import java.time.LocalDate;

import com.codinghavoc.monolith.schoolmanager.enums.AssignmentType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assignment", schema = "school_manager")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long assignmentId;
    
    @NonNull
    @Column(name = "assignment_title")
    private String assignmentTitle;

    @NonNull
    @Column(name = "assignment_type")
    @Enumerated(EnumType.STRING)
    private AssignmentType assignmentType;

    @NonNull
    @Column(name = "assignment_due_date")
    private LocalDate assignmentDueDate;

    @NonNull
    @JoinColumn(name = "teacher_id")
    private Long teacherId;

    @Override
    public String toString() {
        return "Assignment [assignmentTitle=" + assignmentTitle + ", assignmentType=" + assignmentType + "]";
    }
}
