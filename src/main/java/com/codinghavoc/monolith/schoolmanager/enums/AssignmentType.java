package com.codinghavoc.monolith.schoolmanager.enums;

/*
 * This is likely going to need to be a final list, especially if the eventual plan is to 
 * calculate overall grade with each type being grouped and being a certain percentage of
 * the over all grade for the course (homework assignments count for 40% of over all grade, etc)
 */
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
