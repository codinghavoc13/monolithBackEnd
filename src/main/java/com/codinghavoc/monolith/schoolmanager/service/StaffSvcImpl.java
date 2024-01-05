package com.codinghavoc.monolith.schoolmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codinghavoc.monolith.schoolmanager.dto.SMCourseDTO;
import com.codinghavoc.monolith.schoolmanager.entity.ConfigEntry;
import com.codinghavoc.monolith.schoolmanager.entity.Course;
import com.codinghavoc.monolith.schoolmanager.entity.CourseStudent;
import com.codinghavoc.monolith.schoolmanager.entity.CourseTeacher;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.enums.Role;
import com.codinghavoc.monolith.schoolmanager.repo.ConfigRepo;
import com.codinghavoc.monolith.schoolmanager.repo.CourseRepo;
import com.codinghavoc.monolith.schoolmanager.repo.CourseStudentRepo;
import com.codinghavoc.monolith.schoolmanager.repo.CourseTeacherRepo;
import com.codinghavoc.monolith.schoolmanager.repo.UserRepo;
import com.codinghavoc.monolith.schoolmanager.util.SvcUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StaffSvcImpl implements StaffSvc{
    ConfigRepo configRepo;
    CourseRepo courseRepo;
    CourseStudentRepo csRepo;
    CourseTeacherRepo ctRepo;
    UserRepo userRepo;

    @Override
    public Course addNewCourse(Course course){
        return courseRepo.save(course);
    }

    //TODO take another look at all methods with DIVE exceptions
    /*
     * The code is preventing duplicate entries but when trying to save it will still increment
     * the ct_id or cs_id. Look at updating the code to check whether or not a matching entry 
     * exists first
     */
    @Override
    public ResponseEntity<List<CourseStudent>> assignCoursesToStudent(SMCourseDTO dto){
        List<CourseStudent> result = new ArrayList<>();
        CourseStudent cs = new CourseStudent();
        cs.setStudent_id(dto.student_id);
        for(Long course_id : dto.course_ids){
            cs.setCourse_id(course_id);
            try{
                result.add(csRepo.save(cs));
            } catch (DataIntegrityViolationException e){
                result.add(csRepo.findByCourseStudent(cs.getCourse_id(), cs.getStudent_id()));
            }
            cs.setCourse_id(null);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CourseStudent> assignStudentToCourse(SMCourseDTO dto){
        CourseStudent cs = new CourseStudent();
        CourseStudent result = new CourseStudent();
        cs.setCourse_id(dto.course_id);
        cs.setStudent_id(dto.student_id);
        try {
            result = csRepo.save(cs);
        } catch (DataIntegrityViolationException e) {
            result = csRepo.findByCourseStudent(cs.getCourse_id(), cs.getStudent_id());
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CourseStudent>> assignStudentsToCourse(SMCourseDTO dto){
        List<CourseStudent> result = new ArrayList<>();
        CourseStudent cs = new CourseStudent();
        cs.setCourse_id(dto.course_id);
        for(Long student_id : dto.student_ids){
            cs.setStudent_id(student_id);
            try{
                result.add(csRepo.save(cs));
            } catch (DataIntegrityViolationException e){
                result.add(csRepo.findByCourseStudent(cs.getCourse_id(), cs.getStudent_id()));
            }
            cs.setCourse_id(null);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CourseTeacher> assignTeacherToCourse(SMCourseDTO dto){
        CourseTeacher ct = new CourseTeacher();
        CourseTeacher result = new CourseTeacher();
        ct.setCourse_id(dto.course_id);
        ct.setTeacher_id(dto.teacher_id);
        try {
            result = ctRepo.save(ct);
        } catch (DataIntegrityViolationException e) {
            result = ctRepo.findByCourseTeacher(ct.getCourse_id(), ct.getTeacher_id());
        }
        return new ResponseEntity<CourseTeacher>(result,HttpStatus.OK);
    }

    @Override
    public List<User> getStudentsNotAssignedToTeacher(){
        return SvcUtil.clearPWFromResults((List<User>)userRepo.getStudentsNotAssignedToTeacher());
    }

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
        if(temp.getRole().equals(Role.STUDENT) && temp.getSchoolStudentId() == null){
            ConfigEntry cePrefix = configRepo.getSchoolIdPrefix();
            ConfigEntry ceCounter = configRepo.getSchoolIdCounter();
            String newSchoolId = cePrefix.value + SvcUtil.padString(ceCounter.value);
            temp.setSchoolStudentId(newSchoolId);
            System.out.println(temp.getSchoolStudentId());
            int i = Integer.valueOf(ceCounter.value);
            i++;
            ceCounter.value = String.valueOf(i);
            configRepo.save(ceCounter);
        }
        temp.setVerified(true);
        return userRepo.save(temp);
    }
}
