package com.codinghavoc.monolith.schoolmanager.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codinghavoc.monolith.schoolmanager.dto.SMCourseDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMCourseDetailDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMStudentDetailDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMUserDTO;
import com.codinghavoc.monolith.schoolmanager.entity.ConfigEntry;
import com.codinghavoc.monolith.schoolmanager.entity.Course;
import com.codinghavoc.monolith.schoolmanager.entity.CourseStudent;
import com.codinghavoc.monolith.schoolmanager.entity.CoursePeriodTeacher;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.enums.Role;
import com.codinghavoc.monolith.schoolmanager.repo.ConfigRepo;
import com.codinghavoc.monolith.schoolmanager.repo.CourseRepo;
import com.codinghavoc.monolith.schoolmanager.repo.CourseStudentRepo;
import com.codinghavoc.monolith.schoolmanager.repo.CPTRepo;
import com.codinghavoc.monolith.schoolmanager.repo.UserRepo;
import com.codinghavoc.monolith.schoolmanager.util.SvcUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StaffSvcImpl implements StaffSvc{
    ConfigRepo configRepo;
    CourseRepo courseRepo;
    CourseStudentRepo csRepo;
    CPTRepo cptRepo;
    UserRepo userRepo;

    @Override
    public Course addNewCourse(Course course){
        return courseRepo.save(course);
    }

    @Transactional
    @Override
    public ResponseEntity<List<CourseStudent>> assignStudentsToCourse(SMCourseDTO dto){
        /*
         * Need to take a deep look at this method to handle removing courses and not double
         * adding courses
         */
        List<CourseStudent> result = new ArrayList<>();
        CourseStudent cs;
        for(Long studentId : dto.studentIds){
            for(Long cptId : dto.cptIds){
                if(cptId != -1){
                    cs = new CourseStudent();
                    cs.setStudentId(studentId);
                    cs.setCptId(cptId);
                    /*
                     * preventing duplicates is not a major problem since the table is setup with
                     * a unique constraint to prevent duplicate studentId-cptId entries
                     * 
                     * Need to figure out how to handle removing classes
                     * - Option 1: build two lists by studentId: 1 incoming cptIds, 2 existing cptIds
                     */
                    try{
                        result.add(csRepo.save(cs));
                    } catch (DataIntegrityViolationException e){
                        result.add(csRepo.findByCourseStudent(cs.getStudentId(), cs.getCptId()));
                    }
                }
            }
        }
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

    @Override
    public List<SMStudentDetailDTO> getAllMiddleHighStudents(){
        List<SMStudentDetailDTO> result = new ArrayList<>();
        List<SMUserDTO> students = SvcUtil.convertListUsers(userRepo.getAllMiddleHighStudents());
        SMStudentDetailDTO studentDto;
        SMCourseDetailDTO courseDto;
        Course course;
        User teacher;
        double creditCount = 0.0;
        for(SMUserDTO student : students){
            studentDto = new SMStudentDetailDTO();
            creditCount = 0.0;
            studentDto.enrolledCourses = new ArrayList<>();
            studentDto.student = student;
            List<CoursePeriodTeacher> cpts = cptRepo.getCoursesByStudent(student.userId);
            for(CoursePeriodTeacher cpt : cpts){
                course = SvcUtil.unwrapCourse(courseRepo.findById(cpt.getCourseId()), cpt.getCourseId());
                creditCount+=course.getCredit();
                teacher = SvcUtil.unwrapUser(userRepo.findById(cpt.getTeacherId()), cpt.getTeacherId());
                courseDto = SvcUtil.buildSmCourseDetailDTO(course, teacher, cpt.getPeriod(), cpt.getCptId());
                studentDto.enrolledCourses.add(courseDto);
            }
            studentDto.creditCount = creditCount;
            result.add(studentDto);
        }
        return result;
    }

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
            course = SvcUtil.unwrapCourse(courseRepo.findById(cpt.getCourseId()), cpt.getCourseId());
            teacher = SvcUtil.unwrapUser(userRepo.findById(cpt.getTeacherId()), cpt.getTeacherId());
            dto = SvcUtil.buildSmCourseDetailDTO(course, teacher, cpt.getPeriod(), cpt.getCptId());
            result.add(dto);
        }
        return result;
    }

    @Override
    public List<SMCourseDetailDTO> getCoursesByStudent(Long studentId){
        ArrayList<SMCourseDetailDTO> result = new ArrayList<>();
        List<CoursePeriodTeacher> working = cptRepo.getCoursesByStudent(studentId);
        Course course;
        User teacher;
        for(CoursePeriodTeacher cpt : working){
            course = SvcUtil.unwrapCourse(courseRepo.findById(cpt.getCourseId()), cpt.getCourseId());
            teacher = SvcUtil.unwrapUser(userRepo.findById(cpt.getTeacherId()), cpt.getTeacherId());
            result.add(SvcUtil.buildSmCourseDetailDTO(course, teacher, cpt.getPeriod(), cpt.getCptId()));
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

    /*
     * TEST METHODS ONLY
     */
    @Override
    public ResponseEntity<List<CourseStudent>> testAssign(){
        List<CourseStudent> result = new ArrayList<>();
        /*
         * Need to take a deep look at this method to handle removing courses and not double
         * adding courses
         */
        //Need to mock out the incoming data
        SMCourseDTO dto = new SMCourseDTO();
        //single student, multiple courses
        dto.studentIds = new ArrayList<>(Arrays.asList(1l));
        dto.cptIds = new ArrayList<>(Arrays.asList(1l,2l,3l,4l));
        
        //multiple students, single course
        // dto.studentIds = new ArrayList<>(Arrays.asList(1l,2l,3l,4l));
        // dto.cptIds = new ArrayList<>(Arrays.asList(1l));


        //Need to mock out data from the database
        List<CourseStudent> mockCsData = buildMockCSData();

        /*
         * By studentId, need to compare the incoming studentId/cptId pairs against
         * the mock data coming from the db; 
         *      if an incoming cs exists in the db, do nothing
         *      if an incoming cs does not exist in db, add to a new 'add' list
         *      if a mock cs is not in the incoming list, add to a new 'delete' list
         */

        CourseStudent workingCs;
        List<CourseStudent>working = new ArrayList<>();
        List<CourseStudent> add = new ArrayList<>();
        List<CourseStudent> delete = new ArrayList<>();
        for(Long studentId : dto.studentIds){
            add = new ArrayList<>();
            delete = new ArrayList<>();
            working = new ArrayList<>();
            for(Long cptId : dto.cptIds){
                workingCs = new CourseStudent();
                workingCs.setStudentId(studentId);
                workingCs.setCptId(cptId);
                working.add(workingCs);
                if(mockCsData.contains(workingCs)){
                    //do nothing
                    // add.add(workingCs);
                } else {
                    add.add(workingCs);
                }
            }
            //check if entry exists in db
            //actual call to db
            // if(csRepo.findByCourseStudent(studentId, cptId)!=null){

            // }
            //mock call
            
        }
        for(CourseStudent csCheck: mockCsData){
            if(!working.contains(csCheck)){
                delete.add(csCheck);
            }
        }
        // return new ResponseEntity<>(add,HttpStatus.OK);
        return new ResponseEntity<>(delete,HttpStatus.OK);
    }

    private List<CourseStudent> buildMockCSData(){
        Long csId = 1L;
        ArrayList<CourseStudent> result = new ArrayList<>();
        CourseStudent cs = new CourseStudent();
        cs.setStId(csId++);
        cs.setStudentId(1L);
        cs.setCptId(1l);
        result.add(cs);
        
        // cs = new CourseStudent();
        // cs.setStId(csId++);
        // cs.setStudentId(1L);
        // cs.setCptId(2l);
        // result.add(cs);
        
        cs = new CourseStudent();
        cs.setStId(csId++);
        cs.setStudentId(1L);
        cs.setCptId(3l);
        result.add(cs);
        
        cs = new CourseStudent();
        cs.setStId(csId++);
        cs.setStudentId(1L);
        cs.setCptId(4l);
        result.add(cs);
        
        cs = new CourseStudent();
        cs.setStId(csId++);
        cs.setStudentId(1L);
        cs.setCptId(5l);
        result.add(cs);
        
        cs = new CourseStudent();
        cs.setStId(csId++);
        cs.setStudentId(1L);
        cs.setCptId(6l);
        result.add(cs);
        
        // cs = new CourseStudent();
        // cs.setStId(csId++);
        // cs.setStudentId(1L);
        // cs.setCptId(7l);
        // result.add(cs);
        
        // cs = new CourseStudent();
        // cs.setStId(csId++);
        // cs.setStudentId(1L);
        // cs.setCptId(8l);
        // result.add(cs);

        return result;
    }
}
