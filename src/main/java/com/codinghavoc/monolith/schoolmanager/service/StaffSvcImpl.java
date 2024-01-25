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
import com.codinghavoc.monolith.schoolmanager.entity.CoursePeriodTeacher;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.enums.CourseBlock;
import com.codinghavoc.monolith.schoolmanager.enums.Role;
import com.codinghavoc.monolith.schoolmanager.repo.ConfigRepo;
import com.codinghavoc.monolith.schoolmanager.repo.CourseRepo;
import com.codinghavoc.monolith.schoolmanager.repo.CourseStudentRepo;
import com.codinghavoc.monolith.schoolmanager.repo.CTPRepo;
import com.codinghavoc.monolith.schoolmanager.repo.UserRepo;
import com.codinghavoc.monolith.schoolmanager.util.SvcUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StaffSvcImpl implements StaffSvc{
    ConfigRepo configRepo;
    CourseRepo courseRepo;
    CourseStudentRepo csRepo;
    CTPRepo cptRepo;
    UserRepo userRepo;

    @Override
    public Course addNewCourse(Course course){
        return courseRepo.save(course);
    }

    //TODO take another look at all methods with DIVE exceptions
    //TODO need to rework this in conjunction with changes to the front end to pass back the cptId
    /*
     * The code is preventing duplicate entries but when trying to save it will still increment
     * the ct_id or cs_id. Look at updating the code to check whether or not a matching entry 
     * exists first
     */
    @Override
    public ResponseEntity<List<CourseStudent>> assignCoursesToStudent(SMCourseDTO dto){
        List<CourseStudent> result = new ArrayList<>();
        // CourseStudent cs = new CourseStudent();
        // cs.setStudentId(dto.studentId);
        // for(Long course_id : dto.courseIds){
        //     cs.setCourseId(course_id);
        //     try{
        //         result.add(csRepo.save(cs));
        //     } catch (DataIntegrityViolationException e){
        //         result.add(csRepo.findByCourseStudent(cs.getCourseId(), cs.getStudentId()));
        //     }
        //     cs.setCourseId(null);
        // }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    //TODO need to rework this in conjunction with changes to the front end to pass back the cptId
    @Override
    public ResponseEntity<CourseStudent> assignStudentToCourse(SMCourseDTO dto){
        CourseStudent cs = new CourseStudent();
        CourseStudent result = new CourseStudent();
        // cs.setCourseId(dto.courseId);
        // cs.setStudentId(dto.studentId);
        // try {
        //     result = csRepo.save(cs);
        // } catch (DataIntegrityViolationException e) {
        //     result = csRepo.findByCourseStudent(cs.getCourseId(), cs.getStudentId());
        // }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    //TODO need to rework this in conjunction with changes to the front end to pass back the cptId
    @Transactional
    @Override
    public ResponseEntity<List<CourseStudent>> assignStudentsToCourse(SMCourseDTO dto){
        System.out.println(dto.toString());
        List<CourseStudent> result = new ArrayList<>();
        // List<CourseStudent> temp = new ArrayList<>();
        // CourseStudent cs;
        // for(Long student_id : dto.studentIds){
        //     cs = new CourseStudent();
        //     cs.setCourseId(dto.courseId);
        //     cs.setStudentId(student_id);
        //     cs.setTeacherId(dto.teacherId);
        //     System.out.println(cs.toString());
        //     temp.add(cs);
        //     try{
        //         result.add(csRepo.save(cs));
        //     } catch (DataIntegrityViolationException e){
        //         result.add(csRepo.findByCourseStudent(cs.getCourseId(), cs.getStudentId()));
        //     }
        // }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CoursePeriodTeacher> assignTeacherToCourse(SMCourseDTO dto){
        CoursePeriodTeacher ct = new CoursePeriodTeacher();
        CoursePeriodTeacher result = new CoursePeriodTeacher();
        ct.setCourseId(dto.courseId);
        ct.setTeacherId(dto.teacherId);
        ct.setPeriod(dto.period);
        try {
            result = cptRepo.save(ct);
        } catch (DataIntegrityViolationException e) {
            result = cptRepo.findByCourseTeacher(ct.getCourseId(), ct.getTeacherId(),ct.getPeriod());
        }
        return new ResponseEntity<CoursePeriodTeacher>(result,HttpStatus.OK);
    }

    /*TODO this method is going to need to be reworked */
    @Override
    public List<SMCourseDetailDTO> getCourseDetails(String term){
        List<SMCourseDetailDTO> result = new ArrayList<>();
        ArrayList<CoursePeriodTeacher> cptList = new ArrayList<>();
        if(term.equals("middlehigh")){
            cptList = (ArrayList<CoursePeriodTeacher>) cptRepo.getMiddleHighCourses();
        }
        if(term.equals("elementary")){
            cptList = (ArrayList<CoursePeriodTeacher>) cptRepo.getElementaryCourses();
        }
        SMCourseDetailDTO dto;
        Course course;
        User teacher;
        for(CoursePeriodTeacher cpt : cptList){
            dto = new SMCourseDetailDTO();
            course = SvcUtil.unwrapCourse(courseRepo.findById(cpt.getCourseId()), cpt.getCourseId());
            teacher = SvcUtil.unwrapUser(userRepo.findById(cpt.getTeacherId()), cpt.getTeacherId());
            dto.courseId = course.getCourseId();
            dto.courseName = course.getCourseName();
            dto.courseBlock = course.getCourseBlock();
            dto.credit = course.getCredit();
            dto.period = cpt.getPeriod();
            dto.teacherFirstName = teacher.getFirstName();
            dto.teacherLastName = teacher.getLastName();
            dto.teacherId = teacher.getUserId();
            result.add(dto);
        }
        return result;
    }

    @Override
    public List<SMUserDTO> getStudentsByGrade(String gradeLevel){
        List<SMUserDTO> result = new ArrayList<>();
        result = SvcUtil.convertListUsers(userRepo.getStudentsByGradeLevel(gradeLevel));
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
