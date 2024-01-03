package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.schoolmanager.dto.SMLoginDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Relationship;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.repo.AssignmentRepo;
import com.codinghavoc.monolith.schoolmanager.repo.GradeEntryRepo;
import com.codinghavoc.monolith.schoolmanager.repo.RelationshipRepo;
import com.codinghavoc.monolith.schoolmanager.repo.UserRepo;
import com.codinghavoc.monolith.schoolmanager.util.PasswordHashUtil;
import com.codinghavoc.monolith.schoolmanager.util.SvcUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserSvcImpl implements UserSvc{
    AssignmentRepo assignmentRepo;
    GradeEntryRepo geRepo;
    UserRepo userRepo;
    RelationshipRepo relRepo;

    @Override
    public Relationship addRelationship(SMReqDTO dto){
        System.out.println(dto.relationshipType);
        Relationship rel = new Relationship();
        rel.setStudent_id(dto.student_id);
        rel.setRelative_id(dto.relative_id);
        rel.setRelationship(dto.relationshipType);
        return relRepo.save(rel);
    }

    @Override
    public Boolean checkUsername(SMRegisterDTO check){
        return getUserNames().contains(check.username);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>)userRepo.findAll();
    }

    @Override
    public List<User> getAllUsersNoPW() {
        return SvcUtil.clearPWFromResults((List<User>)userRepo.findAll());
    }

    @Override
    public List<User> getRelatives(Long student_id){
        List<User> result = new ArrayList<>();
        List<Relationship> temp = relRepo.getRelativesByStudentId(student_id);
        for(Relationship r : temp){
            result.add(SvcUtil.unwrapUser(userRepo.findById(r.getRelative_id()), r.getRelative_id()));
        }
        return result;
    }

    @Override
    public User getUser(Long id) {
        return SvcUtil.unwrapUser(userRepo.findById(id),id);
    }
    
    @Override
    public List<String> getUserNames(){
        return (List<String>)userRepo.getUserNames();
    }

    @Override
    public List<User> getUsersByRole(String role){
        return SvcUtil.clearPWFromResults((List<User>)userRepo.getUsersByRoleLastNameAsc(role));
    }

    /*
     * Possibly rework this to return a response entity
     */
    @Override
    public User login(SMLoginDTO dto){
        User check = userRepo.getStaffByUsername(dto.username);
        // System.out.println(check);
        if(check != null){
            boolean valid = PasswordHashUtil.validateWithPBKDF(dto.password, check.getPasswordSalt(), check.getPasswordHash());
            if(valid) {
                return check;
            } else {
                return null;
            }
        } else return null;
    }
}
