package com.codinghavoc.monolith.schoolmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.schoolmanager.dto.SMAssignmentDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.GradeEntry;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.repo.AssignmentRepo;
import com.codinghavoc.monolith.schoolmanager.repo.GradeEntryRepo;
import com.codinghavoc.monolith.schoolmanager.repo.UserRepo;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TeacherSvcImpl implements TeacherSvc {
    AssignmentRepo assignmentRepo;
    GradeEntryRepo geRepo;
    UserRepo userRepo;

    @Override
    public List<Assignment> getAssignmentsByTeacherId(Long teacher_Id){
        SMReqDTO response = new SMReqDTO();
        response.teacher_id = teacher_Id;
        response.assignments = (List<Assignment>) assignmentRepo.findAllAssignmentByTeacherId(teacher_Id);
        return (List<Assignment>) assignmentRepo.findAllAssignmentByTeacherId(teacher_Id);
    }

    // @Override
    // public List<Assignment> getAssignmentsByTeacherIdAndStudentId(Long teacher_id, Long student_id){

    // }

    @Override
    public List<User> getStudentsAssignedToTeacher(Long teacher_id){
        return (List<User>)userRepo.getStudentsByTeacherId(teacher_id);
    }
    

    @Transactional
    @Override
    public List<Assignment> saveAssignment(SMReqDTO dto){
        List<Assignment> result = new ArrayList<>();
        List<Assignment> assignmentDtos = dto.assignments;
        System.out.println("assignmentDtos size: " + assignmentDtos.size());
        for(Assignment a : assignmentDtos){
            System.out.println(a.toString());
        }
        System.out.println("dto user_id:" + dto.teacher_id);
        User staff = getStaffMember(dto.teacher_id);
        System.out.println("user: " + staff.getUsername());
        // Assignment temp;
        for(Assignment a : assignmentDtos){
            // temp = d.assignment;
            System.out.println(a.toString());
            a.setTeacher(staff);
            result.add(assignmentRepo.save(a));
        }
        return result;
    }

    @Override
    public GradeEntry saveGradeEntry(SMReqDTO dto){
        GradeEntry check = geRepo.findByStudentAndAssignmentId(dto.student_id, dto.assignment_id);
        if(check!=null){
            return check;
        }
        GradeEntry ge = new GradeEntry(dto.teacher_id,dto.student_id,dto.assignment_id, dto.grade);
        return geRepo.save(ge);
    }

    public User getStaffMember(Long id) {
        return SvcUtil.unwrapUser(userRepo.findById(id),id);
    }
}
