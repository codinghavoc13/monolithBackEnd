package com.codinghavoc.monolith.schoolmanager.enums;

public enum AssignmentType {
    HOMEWORK("homework"),
    QUIZ("quiz"),
    TEST("test"),
    REPORT("report");

    public String value;

    private AssignmentType(String v){
        value = v;
    }
}
