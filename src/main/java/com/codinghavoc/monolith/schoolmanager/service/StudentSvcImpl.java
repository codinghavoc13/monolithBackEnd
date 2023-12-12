package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.schoolmanager.entity.Student;
import com.codinghavoc.monolith.schoolmanager.exception.StudentNotFoundException;
import com.codinghavoc.monolith.schoolmanager.repo.StudentRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StudentSvcImpl implements StudentSvc{
    private StudentRepo repo;

    @Override
    public List<Student> getAllStudents(){
        return (List<Student>)repo.findAll();
    }

    @Override
    public Student getStudent(Long id){
        Optional<Student> student = repo.findById(id);
        return unwrapStudent(student,id);
    }

    @Override
    public Student saveStudent(Student student) {
        return repo.save(student);
    }
    
    static Student unwrapStudent(Optional<Student> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new StudentNotFoundException(id);
    }
}
