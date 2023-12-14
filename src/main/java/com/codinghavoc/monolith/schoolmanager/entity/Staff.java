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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "staff", schema = "school_manager")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Long staff_id;

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
    @Column(name = "username")
    private String username;

    @NonNull
    @Column(name = "pwHash")
    private String passwordHash;

    // @NonNull
    @Column(name = "pwSalt")
    private String passwordSalt;

    @NonNull
    @Column(name = "email")
    private String emailString;

    @NonNull
    @Column(name = "phone")
    private String phoneString;
    
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name="student_teacher",
        schema = "school_manager",
        joinColumns = @JoinColumn(name="staff_id",referencedColumnName = "staff_id"),
        inverseJoinColumns = @JoinColumn(name="student_id", referencedColumnName = "student_id")
    )
    private Set<Student>students;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    private Set<Assignment> assignments;
}

/*
Will need to refer back to this to double check when saving data to db
 * https://www.baeldung.com/jpa-persisting-enums-in-jpa
 */
