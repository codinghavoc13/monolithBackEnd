package com.codinghavoc.monolith.schoolmanager.enums;

public enum RelationshipType {
    PRIMARY("primary"),
    PARENT("parent"),
    SIBLING("sibling"),
    GUARDIAN("guardian");

    public String value;

    private RelationshipType(String v){
        value = v;
    }
}
