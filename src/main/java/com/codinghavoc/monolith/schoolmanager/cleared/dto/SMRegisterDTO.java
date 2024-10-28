package com.codinghavoc.monolith.schoolmanager.cleared.dto;

import com.codinghavoc.monolith.schoolmanager.cleared.enums.Role;

//Moved to UserService
public class SMRegisterDTO {
    public String firstName;
    public String lastName;
    public String emailString;
    public String username;
    public String password;
    public String phoneString;
    public String schoolStudentId;
    public String gradeLevel;
    public Role role;
    public boolean verified;
    @Override
    public String toString() {
        return "SMRegisterDTO [firstName=" + firstName + ", lastName=" + lastName + ", email=" + emailString + ", username="
                + username + ", password=" + password + ", phoneString=" + phoneString + ", schoolStudentId="
                + schoolStudentId + ", gradeLevel=" + gradeLevel + ", role=" + role + ", verified=" + verified + "]";
    }
}
