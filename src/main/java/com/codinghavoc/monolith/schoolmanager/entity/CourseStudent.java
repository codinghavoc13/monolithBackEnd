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
@Table(name = "course_student", schema = "school_manager",uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id","cpt_id"})})
public class CourseStudent {

    //need to update this to csId
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="st_id")
    private Long stId;

    @NonNull
    @Column(name = "student_id")
    private Long studentId;

    @NonNull
    @Column(name = "cpt_id")
    private Long cptId;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((studentId == null) ? 0 : studentId.hashCode());
        result = prime * result + ((cptId == null) ? 0 : cptId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CourseStudent other = (CourseStudent) obj;
        if (studentId == null) {
            if (other.studentId != null)
                return false;
        } else if (!studentId.equals(other.studentId))
            return false;
        if (cptId == null) {
            if (other.cptId != null)
                return false;
        } else if (!cptId.equals(other.cptId))
            return false;
        return true;
    }
    
}
