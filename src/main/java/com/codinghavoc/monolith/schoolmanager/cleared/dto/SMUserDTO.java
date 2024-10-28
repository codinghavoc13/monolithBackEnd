package com.codinghavoc.monolith.schoolmanager.cleared.dto;

import com.codinghavoc.monolith.schoolmanager.cleared.entity.User;
import com.codinghavoc.monolith.schoolmanager.cleared.enums.Role;

//Moved to UserService
public class SMUserDTO {
    public Long userId;
    public String firstName;
    public String lastName;
    public String emailString;
    public String phoneString;
    public boolean verified;
    public String schoolStudentId;
    public String gradeLevel;
    public Role role;
    public String username;

    public SMUserDTO(User user){
        userId = user.getUserId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        emailString = user.getEmailString();
        phoneString = user.getPhoneString();
        verified = user.isVerified();
        schoolStudentId = user.getSchoolStudentId();
        gradeLevel = user.getGradeLevel();
        role = user.getRole();
        username = user.getUsername();
    }
}
