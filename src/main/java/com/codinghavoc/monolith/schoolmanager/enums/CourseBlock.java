package com.codinghavoc.monolith.schoolmanager.enums;

public enum CourseBlock {
    FULL_YEAR("Full Year"),
    FALL_SEMESTER("Fall Semester"),
    SPRING_SEMESTER("Spring Semester");

    public String value;

    private CourseBlock(String c){
        value = c;
    }
}
