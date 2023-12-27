package com.codinghavoc.monolith.schoolmanager.service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.repo.UserRepo;

public class StaffSvcImpl implements StaffSvc{
    UserRepo userRepo;

    @Transactional
    @Override
    public User addStudentToTeacherRoster(Long teacher_id, Long student_id) {
        Long check = userRepo.checkForStudentTeacherEntry(teacher_id, student_id);
        if(check > 0) {
            return getStaffMember(teacher_id);
        } else {
            User staff = getStaffMember(teacher_id);
            Optional<User> wrappedStudent = userRepo.findById(student_id);
            User unwrappedStudent = SvcUtil.unwrapUser(wrappedStudent, student_id);
            staff.getStudents().add(unwrappedStudent);
            return staff;
        }
    }

    public User getStaffMember(Long id) {
        return SvcUtil.unwrapUser(userRepo.findById(id),id);
    }
}
