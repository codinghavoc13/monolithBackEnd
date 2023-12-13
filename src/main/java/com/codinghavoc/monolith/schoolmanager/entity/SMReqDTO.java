package com.codinghavoc.monolith.schoolmanager.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SMReqDTO {
    public Long staff_id;
    public Long student_id;
    public Long assignment_id;
    public double grade;
    public Assignment assignment;
    public List<SMReqDTO> assignments;
    public List<Student> students;
}
