package com.codinghavoc.monolith.schoolmanager.entity;

import java.util.Set;

import com.codinghavoc.monolith.schoolmanager.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// @AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long student_id;

    @NonNull
    @Column(name = "school_student_id")
    private String school_student_id;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;
    
    @NonNull
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @NonNull
    @Column(name = "grade_level")
    private String gradeLevel;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name="student_teacher",
        joinColumns = @JoinColumn(name="student_id",referencedColumnName = "student_id"),
        inverseJoinColumns = @JoinColumn(name="staff_id", referencedColumnName = "staff_id")
    )
    private Set<Staff>teachers;
    
}
