package com.codinghavoc.monolith.schoolmanager.dto;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.enums.RelationshipType;

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
