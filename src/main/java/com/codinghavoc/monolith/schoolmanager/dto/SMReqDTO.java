package com.codinghavoc.monolith.schoolmanager.dto;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.Staff;
import com.codinghavoc.monolith.schoolmanager.entity.Student;

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
    public List<Staff> staffList;
    public List<Student> students;
}
