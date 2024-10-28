package com.codinghavoc.monolith.schoolmanager.cleared.dto;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.cleared.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.cleared.entity.User;
import com.codinghavoc.monolith.schoolmanager.cleared.enums.RelationshipType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SMReqDTO {
    public Long courseId;
    public Long teacherId;
    public Long studentId;
    public Long parentId;
    public Long assignmentId;
    public double grade;
    public Assignment assignment;
    public User user;
    public SMRegisterDTO student;
    public List<Assignment> assignments;
    public List<User> users;
    public Long relativeId;
    public RelationshipType relationshipType;
}
