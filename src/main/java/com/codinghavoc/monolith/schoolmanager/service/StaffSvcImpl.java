package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.Staff;
import com.codinghavoc.monolith.schoolmanager.entity.Student;
import com.codinghavoc.monolith.schoolmanager.exception.StaffNotFoundException;
import com.codinghavoc.monolith.schoolmanager.exception.StudentNotFoundException;
import com.codinghavoc.monolith.schoolmanager.repo.AssignmentRepo;
import com.codinghavoc.monolith.schoolmanager.repo.StaffRepo;
import com.codinghavoc.monolith.schoolmanager.repo.StudentRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StaffSvcImpl implements StaffSvc {
    AssignmentRepo assignmentRepo;
    StaffRepo staffRepo;
    StudentRepo studentRepo;

    // public List<Assignment> getAssignmentsByTeacherId(Long teacher_Id){
    //     return (List<Assignment>) assignmentRepo.f(teacher_Id);
    // }

    @Override
    public Staff getStaffMember(Long id) {
        Optional<Staff> student = staffRepo.findById(id);
        return unwrapStaff(student,id);
    }

    @Override
    public List<Student> getStudentsAssignedToTeacher(Long teacher_id){
        return (List<Student>)studentRepo.getStudentsByTeacherId(teacher_id);
    }

    @Override
    public List<Staff> getAllStaff() {
        return (List<Staff>)staffRepo.findAll();
    }

    @Transactional
    @Override
    public String addStudentToTeacherRoster(Long teacher_id, Long student_id) {
        Long check = staffRepo.checkForStudentTeacherEntry(teacher_id, student_id);
        if(check > 0) {
            return "Entry pair exists";
        } else {
            Staff staff = getStaffMember(teacher_id);
            Optional<Student> wrappedStudent = studentRepo.findById(student_id);
            Student unwrappedStudent = unwrapStudent(wrappedStudent, student_id);
            staff.getStudents().add(unwrappedStudent);
            return "Entry pair does not exist";
        }
    }

    @Override
    public Assignment saveAssignment(Assignment assignment, Long teacher_id){
        assignment.setTeacher(getStaffMember(teacher_id));
        return assignmentRepo.save(assignment);
    }

    @Override
    public Staff saveStaff(Staff staff){
        return staffRepo.save(staff);
    }

    static Staff unwrapStaff(Optional<Staff> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new StaffNotFoundException(id);
    }

    static Student unwrapStudent(Optional<Student> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new StudentNotFoundException(id);
    }
    
}
