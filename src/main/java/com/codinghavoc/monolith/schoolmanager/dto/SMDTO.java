package com.codinghavoc.monolith.schoolmanager.dto;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.entity.Course;
import com.codinghavoc.monolith.schoolmanager.entity.User;

public class SMDTO {
    public Course course;
    public List<Course> courses;
    public List<SMDTO> enrolledCourses;
    public List<User> parents;
    public User student;
    public List<User> students;
    public User teacher;
    public List<User> teachers;
}

/*
 * Scenarios:
 * - student details including the student information, the course(s) they are enrolled in, 
 *      the teacher(s) of those courses
 * - getting course details for assigning a student to a course would involve returning a
 *      a list of dtos with a course and a teacher
 * 
 * 
 * Thoughts:
 * - single and list versions or just list versions? going with single and list versions
 * - need to rethink returning dtos for student details and including course/teacher 
 *      information because of this: how to link courses with teachers? idea: use another
 *      SMDTO setting the course and teacher to the course/teacher pair
 */