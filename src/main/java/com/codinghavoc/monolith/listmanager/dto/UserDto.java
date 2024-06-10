package com.codinghavoc.monolith.listmanager.dto;

import com.codinghavoc.monolith.listmanager.entity.User;

public class UserDto {
    public String firstName;
    public String lastName;
    public String email;
    public String pwClear;
    public Long userId;

    public UserDto(){};

    public UserDto(User user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmailAddress();
        this.userId = user.getUserId();
        this.pwClear = "";
    }
}
