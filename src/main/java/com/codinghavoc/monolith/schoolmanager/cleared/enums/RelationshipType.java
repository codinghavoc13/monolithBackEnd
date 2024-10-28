package com.codinghavoc.monolith.schoolmanager.cleared.enums;

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
