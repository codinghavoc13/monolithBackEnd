package com.codinghavoc.monolith.schoolmanager.entity;

import com.codinghavoc.monolith.schoolmanager.enums.AssignmentType;

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
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AssignmentType type;

    // @NonNull
    // @JsonIgnore
    // @ManyToOne
    // @Column(name = "staff_id")
    // private Long staffId;

    @ManyToOne()
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff teacher;

}
