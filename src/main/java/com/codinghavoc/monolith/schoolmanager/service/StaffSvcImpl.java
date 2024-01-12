package com.codinghavoc.monolith.schoolmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codinghavoc.monolith.schoolmanager.dto.SMCourseDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMCourseDetailDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMUserDTO;
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

    @Transactional
    @Override
    public ResponseEntity<List<CourseStudent>> assignStudentsToCourse(SMCourseDTO dto){
        System.out.println(dto.toString());
        List<CourseStudent> result = new ArrayList<>();
        List<CourseStudent> temp = new ArrayList<>();
        CourseStudent cs;
        for(Long student_id : dto.student_ids){
            cs = new CourseStudent();
            cs.setCourse_id(dto.course_id);
            cs.setStudent_id(student_id);
            System.out.println(cs.toString());
            temp.add(cs);
            try{
                result.add(csRepo.save(cs));
            } catch (DataIntegrityViolationException e){
                result.add(csRepo.findByCourseStudent(cs.getCourse_id(), cs.getStudent_id()));
            }
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

    public List<SMCourseDetailDTO> getCourseDetails(){
        List<SMCourseDetailDTO> result = new ArrayList<>();
        List<Course> courses = (List<Course>)courseRepo.findAll();
        SMCourseDetailDTO dto;
        User teacher;
        for(Course course : courses){
            dto = new SMCourseDetailDTO();
            dto.course_id = course.getCourse_id();
            dto.courseName = course.getCourseName();
            dto.courseLength = course.getCourseLength();
            //need to add a check to only get details from courses that have a teacher
            teacher = userRepo.getTeacherByCourseId(course.getCourse_id());
            if(teacher!=null){
                dto.teacherFirstName = teacher.getFirstName();
                dto.teacherLastName = teacher.getLastName();
                result.add(dto);
            }
            //not adding courses that do not have a teacher
        }
        return result;
    }

    @Override
    public List<SMUserDTO> getStudentsNotAssignedToTeacher(){
        return SvcUtil.convertListUsers((List<User>)userRepo.getStudentsNotAssignedToTeacher());
    }

    public User getStaffMember(Long id) {
        return SvcUtil.unwrapUser(userRepo.findById(id),id);
    }

    @Override
    public List<SMUserDTO> getUnverifiedUsers(){
        return SvcUtil.convertListUsers((List<User>)userRepo.getUnverifiedUsers());
    }

    @Override
    public SMUserDTO updateUserVerification(Long id){
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
        return new SMUserDTO(userRepo.save(temp));
    }
}
