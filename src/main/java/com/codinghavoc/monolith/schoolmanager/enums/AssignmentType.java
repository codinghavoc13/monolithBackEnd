package com.codinghavoc.monolith.schoolmanager.enums;

public enum AssignmentType {
    HOMEWORK("Homework"),
    QUIZ("Quiz"),
    TEST("Test"),
    REPORT("Report");

    public String value;

    private AssignmentType(String v){
        value = v;
    }
}
