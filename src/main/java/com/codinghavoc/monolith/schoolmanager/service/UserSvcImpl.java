package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.schoolmanager.dto.SMStudentDetailDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMUserDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMCourseDetailDTO;
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
        rel.setStudentId(dto.studentId);
        rel.setRelativeId(dto.relativeId);
        rel.setRelationship(dto.relationshipType);
        return relRepo.save(rel);
    }

    @Override
    public Boolean checkUsername(SMRegisterDTO check){
        return getUserNames().contains(check.username);
    }

    @Override
    public SMUserDTO enrollStudent(SMReqDTO dto){
        //save the student with the user repo
        //get the new student as a user
        User newStudent = userRepo.save(new User(dto.student));
        //build a new relationship object with the parent_id, the new student_id, and set role to PARENT
        Relationship rel = new Relationship();
        rel.setStudentId(newStudent.getUserId());
        rel.setRelativeId(dto.parentId);
        rel.setRelationship(RelationshipType.PARENT);
        //save the new relationship object with the relationship repo
        relRepo.save(rel);
        //return the student object
        return new SMUserDTO(newStudent);
    }

    // @Override
    // public List<User> getAllUsers() {
    //     return (List<User>)userRepo.findAll();
    // }

    @Override
    public List<SMUserDTO> getAllUsersSimple() {
        List<SMUserDTO> result = new ArrayList<>();
        for(User user : (List<User>)userRepo.findAll()){
            result.add(new SMUserDTO(user));
        }
        return result;
    }

    @Override
    public List<SMUserDTO> getRelatives(Long student_id){
        List<User> result = new ArrayList<>();
        List<Relationship> temp = relRepo.getRelativesByStudentId(student_id);
        for(Relationship r : temp){
            result.add(SvcUtil.unwrapUser(userRepo.findById(r.getRelativeId()), r.getRelativeId()));
        }
        return SvcUtil.convertListUsers(result);
    }

    @Override
    public List<SMStudentDetailDTO> getStudentDetails(){
        List<SMStudentDetailDTO> result = new ArrayList<>();
        SMStudentDetailDTO dto;
        SMCourseDetailDTO courseDto;
        List<User> teachers;
        List<Course> courses;
        List<SMUserDTO> students = SvcUtil.convertListUsers(userRepo.getUsersByRoleLastNameAsc(Role.STUDENT.toString()));
        for(SMUserDTO student : students){
            dto = new SMStudentDetailDTO();
            dto.enrolledCourses = new ArrayList<>();
            dto.student = student;
            courses = courseRepo.getCoursesAssignedToStudent(student.userId);
            for(Course course : courses){
                teachers = userRepo.getTeacherByCourseId(course.getCourseId());
                if(teachers != null && teachers.size()>0){
                    for(User teacher : teachers){
                        courseDto = buildCourseDetailDTO(course, teacher);
                        dto.enrolledCourses.add(courseDto);
                    }
                }
                
                dto.parents = SvcUtil.convertListUsers(userRepo.getParentsByStudentId(student.userId));
            }
            result.add(dto);
        }
        return result;
    }

    @Override
    public SMStudentDetailDTO getStudentDetails(Long student_id){
        SMStudentDetailDTO result = new SMStudentDetailDTO();
        SMUserDTO student = new SMUserDTO(SvcUtil.unwrapUser(userRepo.findById(student_id), student_id));
        result = buildSMDto(student_id);
        result.student = student;
        return result;
    }

    @Override
    public List<SMUserDTO> getStudentsByParentId(Long parent_id){
        List<User> result = new ArrayList<>();
        List<Relationship> temp = relRepo.getStudentsByParentId(parent_id);
        for(Relationship r : temp){
            result.add(SvcUtil.unwrapUser(userRepo.findById(r.getStudentId()), r.getStudentId()));
        }
        return SvcUtil.convertListUsers(result);
    }

    @Override
    public SMUserDTO getUser(Long id) {
        return new SMUserDTO(SvcUtil.unwrapUser(userRepo.findById(id),id));
    }

    @Override
    public SMUserDTO getUserSimple(Long id) {
        return new SMUserDTO(SvcUtil.unwrapUser(userRepo.findById(id),id));
    }
    
    @Override
    public List<String> getUserNames(){
        return (List<String>)userRepo.getUserNames();
    }

    @Override
    public List<SMUserDTO> getUsersByRole(String role){
        return SvcUtil.convertListUsers((List<User>)userRepo.getUsersByRoleLastNameAsc(role));
    }

    /*
     * Possibly rework this to return a response entity
     */
    @Override
    public SMUserDTO login(SMLoginDTO dto){
        User check = userRepo.getStaffByUsername(dto.username);
        // System.out.println(check);
        if(check != null){
            boolean valid = PasswordHashUtil.validateWithPBKDF(dto.password, check.getPasswordSalt(), check.getPasswordHash());
            if(valid) {
                return new SMUserDTO(check);
            } else {
                return null;
            }
        } else return null;
    }

    @Override
    public SMUserDTO saveUser(SMRegisterDTO dto){
        return new SMUserDTO(userRepo.save(new User(dto)));
    }

    private SMCourseDetailDTO buildCourseDetailDTO(Course course, User teacher){
        SMCourseDetailDTO result = new SMCourseDetailDTO();
        result.courseId = course.getCourseId();
        result.courseName = course.getCourseName();
        result.courseBlock = course.getCourseBlock();
        result.credit = course.getCredit();
        result.teacherFirstName = teacher.getFirstName();
        result.teacherLastName = teacher.getLastName();
        return result;
    }

    private SMStudentDetailDTO buildSMDto(Long student_id){
        SMStudentDetailDTO result = new SMStudentDetailDTO();
        List<Course> courses;
        SMCourseDetailDTO courseDto;
        List<User> teachers;
        courses = courseRepo.getCoursesAssignedToStudent(student_id);
        for(Course course : courses){
            teachers = userRepo.getTeacherByCourseId(course.getCourseId());
            if(teachers != null && teachers.size()>0){
                result.enrolledCourses = new ArrayList<>();
                for(User teacher : teachers){
                    courseDto = buildCourseDetailDTO(course, teacher);
                    result.enrolledCourses.add(courseDto);
                }
            }
            result.parents = SvcUtil.convertListUsers(userRepo.getParentsByStudentId(student_id));
        }
        return result;
    }
}
