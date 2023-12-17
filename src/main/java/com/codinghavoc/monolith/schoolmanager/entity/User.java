package com.codinghavoc.monolith.schoolmanager.entity;

import java.util.Set;

import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.enums.Role;
import com.codinghavoc.monolith.schoolmanager.util.PasswordHashUtil;
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
@Table(name = "users", schema = "school_manager")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

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

    @Column(name = "school_student_id")
    private String school_student_id;

    @Column(name = "grade_level")
    private String gradeLevel;
    
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name="student_teacher",
        schema = "school_manager",
        joinColumns = @JoinColumn(name="teacher_id",referencedColumnName = "user_id"),
        inverseJoinColumns = @JoinColumn(name="student_id", referencedColumnName = "user_id")
    )
    private Set<User>students;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name="student_teacher",
        schema = "school_manager",
        joinColumns = @JoinColumn(name="student_id",referencedColumnName = "user_id"),
        inverseJoinColumns = @JoinColumn(name="teacher_id", referencedColumnName = "user_id")
    )
    private Set<User>teachers;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    private Set<Assignment> assignments;
    
    public User(SMRegisterDTO dto){
        firstName = dto.firstName;
        lastName = dto.lastName;
        emailString = dto.email;
        phoneString = dto.phoneString;
        if(dto.password != null){
            String[] pass;
            pass = PasswordHashUtil.hashPWWPBKDF(dto.password);
            passwordSalt = pass[0];
            passwordHash = pass[1];
        }
        role = dto.role;
        username = dto.username;
        if(role.equals(Role.STUDENT)){
            school_student_id = dto.school_student_id;
            gradeLevel = dto.gradeLevel;
        }

    }
}
