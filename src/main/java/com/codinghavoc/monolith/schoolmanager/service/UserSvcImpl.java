package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.schoolmanager.dto.SMDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMLoginDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Course;
import com.codinghavoc.monolith.schoolmanager.entity.Relationship;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.enums.RelationshipType;
import com.codinghavoc.monolith.schoolmanager.enums.Role;
import com.codinghavoc.monolith.schoolmanager.repo.AssignmentRepo;
import com.codinghavoc.monolith.schoolmanager.repo.CourseRepo;
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
    CourseRepo courseRepo;
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
    public User enrollStudent(SMReqDTO dto){
        //save the student with the user repo
        //get the new student as a user
        User newStudent = userRepo.save(new User(dto.student));
        //build a new relationship object with the parent_id, the new student_id, and set role to PARENT
        Relationship rel = new Relationship();
        rel.setStudent_id(newStudent.getUserId());
        rel.setRelative_id(dto.parent_id);
        rel.setRelationship(RelationshipType.PARENT);
        //save the new relationship object with the relationship repo
        relRepo.save(rel);
        //return the student object
        return newStudent;
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
    public List<SMDTO> getStudentDetails(){
        List<SMDTO> result = new ArrayList<>();
        SMDTO dto;
        SMDTO courseDto;
        User teacher;
        List<Course> courses;
        List<User> students = SvcUtil.clearPWFromResults(userRepo.getUsersByRoleLastNameAsc(Role.STUDENT.toString()));
        for(User student : students){
            dto = new SMDTO();
            dto.student = student;
            courses = courseRepo.getCoursesAssignedToStudent(student.getUserId());
            for(Course course : courses){
                teacher = SvcUtil.clearPWFromResult(userRepo.getTeacherByCourseId(course.getCourse_id()));
                courseDto = new SMDTO();
                courseDto.course = course;
                courseDto.teacher = teacher;
                dto.enrolledCourses = new ArrayList<>();
                dto.enrolledCourses.add(courseDto);
                dto.parents = SvcUtil.clearPWFromResults(userRepo.getParentsByStudentId(student.getUserId()));
            }
            result.add(dto);
        }
        return result;
    }

    @Override
    public SMDTO getStudentDetails(Long student_id){
        SMDTO result = new SMDTO();
        User student = SvcUtil.clearPWFromResult(SvcUtil.unwrapUser(userRepo.findById(student_id), student_id));
        result = builSmdto(student_id);
        result.student = student;
        return result;
    }

    private SMDTO builSmdto(Long student_id){
        SMDTO result = new SMDTO();
        List<Course> courses;
        SMDTO courseDto;
        User teacher;
        courses = courseRepo.getCoursesAssignedToStudent(student_id);
        for(Course course : courses){
            teacher = SvcUtil.clearPWFromResult(userRepo.getTeacherByCourseId(course.getCourse_id()));
            courseDto = new SMDTO();
            courseDto.course = course;
            courseDto.teacher = teacher;
            result.enrolledCourses = new ArrayList<>();
            result.enrolledCourses.add(courseDto);
            result.parents = SvcUtil.clearPWFromResults(userRepo.getParentsByStudentId(student_id));
        }
        return result;
    }

    @Override
    public List<User> getStudentsByParentId(Long parent_id){
        List<User> result = new ArrayList<>();
        List<Relationship> temp = relRepo.getStudentsByParentId(parent_id);
        for(Relationship r : temp){
            result.add(SvcUtil.unwrapUser(userRepo.findById(r.getStudent_id()), r.getStudent_id()));
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

    @Override
    public User saveUser(SMRegisterDTO dto){
        // System.out.println("test-USI");
        // System.out.println(dto);
        return userRepo.save(new User(dto));
    }
}
