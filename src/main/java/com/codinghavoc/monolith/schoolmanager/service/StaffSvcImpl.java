package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.repo.UserRepo;
import com.codinghavoc.monolith.schoolmanager.util.SvcUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
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

    @Override
    public List<User> getStudentsNotAssignedToTeacher(){
        return SvcUtil.clearPWFromResults((List<User>)userRepo.getStudentsNotAssignedToTeacher());
    }

    // @Override
    // public User saveUser(SMRegisterDTO dto){
    //     return userRepo.save(new User(dto));
    // }

    public User getStaffMember(Long id) {
        return SvcUtil.unwrapUser(userRepo.findById(id),id);
    }

    @Override
    public List<User> getUnverifiedUsers(){
        return SvcUtil.clearPWFromResults((List<User>)userRepo.getUnverifiedUsers());
    }

    @Override
    public User updateUserVerification(Long id){
        User temp = SvcUtil.unwrapUser(userRepo.findById(id), id);
        temp.setVerified(true);
        //need to add everything to setup the school student id
        return userRepo.save(temp);
    }
}
