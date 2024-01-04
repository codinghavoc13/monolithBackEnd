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
    public Long teacher_id;
    public Long student_id;
    public Long parent_id;
    public Long assignment_id;
    public double grade;
    public Assignment assignment;
    public User user;
    public List<Assignment> assignments;
    public List<User> users;
    public List<User> students;
    public Long relative_id;
    public RelationshipType relationshipType;
}
