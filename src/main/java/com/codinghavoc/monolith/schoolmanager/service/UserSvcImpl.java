package com.codinghavoc.monolith.schoolmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codinghavoc.monolith.schoolmanager.dto.SMLoginDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.GradeEntry;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.exception.AssignmentNotFoundException;
import com.codinghavoc.monolith.schoolmanager.exception.UserNotFoundException;
import com.codinghavoc.monolith.schoolmanager.repo.AssignmentRepo;
import com.codinghavoc.monolith.schoolmanager.repo.GradeEntryRepo;
import com.codinghavoc.monolith.schoolmanager.repo.UserRepo;
import com.codinghavoc.monolith.schoolmanager.util.PasswordHashUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserSvcImpl implements UserSvc{
    AssignmentRepo assignmentRepo;
    GradeEntryRepo geRepo;
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
            User unwrappedStudent = unwrapUser(wrappedStudent, student_id);
            staff.getStudents().add(unwrappedStudent);
            return staff;
        }
    }

    @Override
    public List<User> getAllStaff() {
        return (List<User>)userRepo.findAll();
    }

    @Override
    public List<Assignment> getAssignmentsByTeacherId(Long teacher_Id){
        return (List<Assignment>) assignmentRepo.findAllAssignmentByStaffId(teacher_Id);
    }

    @Override
    public User getStaffMember(Long id) {
        return unwrapUser(userRepo.findById(id),id);
    }
    
    @Override
    public List<String> getStaffUsernames(){
        return (List<String>)userRepo.getUserNames();
    }

    @Override
    public List<User> getStudentsAssignedToTeacher(Long teacher_id){
        return (List<User>)userRepo.getStudentsByTeacherId(teacher_id);
    }

    @Override
    public User login(SMLoginDTO dto){
        User check = userRepo.getStaffByUsername(dto.username);
        boolean valid = PasswordHashUtil.validateWithPBKDF(dto.password, check.getPasswordSalt(), check.getPasswordHash());
        if(valid) {
            return check;
        } else {
            return null;
        }
    }

    @Override
    public List<Assignment> saveAssignment(SMReqDTO dto){
        List<Assignment> result = new ArrayList<>();
        List<SMReqDTO> assignmentDtos = dto.assignments;
        User staff = getStaffMember(dto.staff_id);
        Assignment temp;
        for(SMReqDTO d : assignmentDtos){
            temp = d.assignment;
            temp.setTeacher(staff);
            result.add(assignmentRepo.save(temp));
        }
        return result;
    }

    @Override
    public GradeEntry saveGradeEntry(SMReqDTO dto){
        GradeEntry check = geRepo.findByStudentAndAssignmentId(dto.student_id, dto.assignment_id);
        if(check!=null){
            return check;
        }
        GradeEntry ge = new GradeEntry(dto.staff_id,dto.student_id,dto.assignment_id, dto.grade);
        return geRepo.save(ge);
    }

    @Override
    public User saveUser(SMRegisterDTO dto){
        return userRepo.save(new User(dto));
    }

    @Override
    public List<User> saveUsers(List<SMRegisterDTO> users){
        List<User> result = new ArrayList<>();
        for(SMRegisterDTO dto : users){
            result.add(userRepo.save(new User(dto)));
        }
        return result;

    }

    static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new UserNotFoundException(id);
    }

    static Assignment unwrapAssignment(Optional<Assignment> entity, Long id){
        if(entity.isPresent()) return entity.get();
        else throw new AssignmentNotFoundException(id);
    }    
}
