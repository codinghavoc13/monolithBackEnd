package com.codinghavoc.monolith.schoolmanager.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codinghavoc.monolith.schoolmanager.entity.Course;
import com.codinghavoc.monolith.schoolmanager.entity.CourseTeacher;

public interface CourseRepo extends CrudRepository<Course,Long>{    
}
