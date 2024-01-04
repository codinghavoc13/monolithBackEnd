package com.codinghavoc.monolith.schoolmanager.enums;

public enum Role {
    PRIMARY("primary"),
    STUDENT("student"),
    PARENT("parent"),
    TEACHER("teacher"),
    ADMIN("admin"),
    SUPER_ADMIN("super_admin"),
    EMERGENCY_CONTACT("emergency_contact");

    public String value;

    private Role(String v){
        value = v;
    }
}
