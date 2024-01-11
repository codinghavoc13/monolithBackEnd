package com.codinghavoc.monolith.schoolmanager.entity;

import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.enums.Role;
import com.codinghavoc.monolith.schoolmanager.util.PasswordHashUtil;

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
    
    // @NonNull
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    // @NonNull
    @Column(name = "username")
    private String username;

    // @NonNull
    @Column(name = "pwHash")
    private String passwordHash;

    // @NonNull
    @Column(name = "pwSalt")
    private String passwordSalt;

    // @NonNull
    @Column(name = "email_string")
    private String emailString;

    // @NonNull
    @Column(name = "phone")
    private String phoneString;

    @Column(name = "school_student_id")
    private String schoolStudentId;

    @Column(name = "grade_level")
    private String gradeLevel;

    @Column(name="verified")
    private boolean verified;

    /*
     * TODO: set up a new list for family
     * - allow for relationships to multiple users
     * - primary user would need to have a full account (username, password), all other relationships need 
     * minimum first and last name, phone, relationship (relationship would need to be added)
     * - this would likely be a many-to-many junction table (student_id, relationship_id, relation_type)
     */
    
    public User(SMRegisterDTO dto){
        firstName = dto.firstName;
        lastName = dto.lastName;
        emailString = dto.emailString;
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
            schoolStudentId = dto.schoolStudentId;
            gradeLevel = dto.gradeLevel;
        }
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", role=" + role
                + ", username=" + username + ", emailString=" + emailString + ", phoneString=" + phoneString
                + ", schoolStudentId=" + schoolStudentId + ", gradeLevel=" + gradeLevel + ", verified=" + verified
                + "]";
    }
}
