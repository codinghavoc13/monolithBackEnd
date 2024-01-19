package com.codinghavoc.monolith.schoolmanager.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import com.codinghavoc.monolith.schoolmanager.enums.RelationshipType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="relationships", schema="school_manager",uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id","relative_id"})})
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relation_id")
    private Long relationId;

    @NonNull
    @Column(name="student_id")
    private Long studentId;

    @NonNull
    @Column(name = "relative_id")
    private Long relativeId;

    @NonNull
    @Column(name="relationship")
    @Enumerated(EnumType.STRING)
    private RelationshipType relationship;
}
