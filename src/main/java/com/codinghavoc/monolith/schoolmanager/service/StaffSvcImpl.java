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
import com.codinghavoc.monolith.schoolmanager.dto.SMFullCourseDetailDTO;
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
        CourseStudent workingCs;
        List<CourseStudent> working = new ArrayList<>();
        List<CourseStudent> existing = new ArrayList<>();
        List<CourseStudent> addList = new ArrayList<>();
        List<CourseStudent> deleteList = new ArrayList<>();
        for(Long studentId : dto.studentIds){
            addList = new ArrayList<>();
            deleteList = new ArrayList<>();
            working = new ArrayList<>();
            existing = csRepo.findByStudentId(studentId);
            for(Long cptId : dto.cptIds){
                workingCs = new CourseStudent();
                workingCs.setStudentId(studentId);
                workingCs.setCptId(cptId);
                working.add(workingCs);
                if(!existing.contains(workingCs)){
                    addList.add(workingCs);
                }
            }
            for(CourseStudent cs : existing){
                if(!working.contains(cs)){
                    deleteList.add(cs);
                }
            }
            // loop through delete and delete those from the database
            System.out.println("Delete: " + deleteList.size());
            for(CourseStudent delCs : deleteList){
                csRepo.delete(delCs);
            }
            System.out.println("Add: " + addList.size());
            for(CourseStudent addCs : addList){
                csRepo.save(addCs);
            }
            result.addAll(working);
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
        for(SMUserDTO student : students){
            studentDto = buildStudentDetailDTO(student);
            result.add(studentDto);
        }
        return result;
    }

    private SMStudentDetailDTO buildStudentDetailDTO(SMUserDTO student){
        SMStudentDetailDTO result = new SMStudentDetailDTO();
        double creditCount = 0.0;
        Course course;
        SMCourseDetailDTO courseDto;
        User teacher;
        result.enrolledCourses = new ArrayList<>();
        result.student = student;
            List<CoursePeriodTeacher> cpts = cptRepo.getCoursesByStudent(student.userId);
            for(CoursePeriodTeacher cpt : cpts){
                course = SvcUtil.unwrapCourse(courseRepo.findById(cpt.getCourseId()), cpt.getCourseId());
                creditCount+=course.getCredit();
                teacher = SvcUtil.unwrapUser(userRepo.findById(cpt.getTeacherId()), cpt.getTeacherId());
                courseDto = SvcUtil.buildSmCourseDetailDTO(course, teacher, cpt.getPeriod(), cpt.getCptId());
                result.enrolledCourses.add(courseDto);
            }
            result.creditCount = creditCount;
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
    public List<SMFullCourseDetailDTO> getFullCourseDetails(){
        List<SMFullCourseDetailDTO> result = new ArrayList<>();
        List<CoursePeriodTeacher> cpts = (List<CoursePeriodTeacher>) cptRepo.findAll();
        SMFullCourseDetailDTO fullDto;
        List<CourseStudent> courseStudents;
        Course course;
        User teacher;
        User student;
        for(CoursePeriodTeacher cpt : cpts){
            fullDto = new SMFullCourseDetailDTO();
            course = SvcUtil.unwrapCourse(courseRepo.findById(cpt.getCourseId()), cpt.getCourseId());
            teacher = SvcUtil.unwrapUser(userRepo.findById(cpt.getTeacherId()), cpt.getTeacherId());
            fullDto.course = (SvcUtil.buildSmCourseDetailDTO(course, teacher, cpt.getPeriod(), cpt.getCptId()));
            courseStudents = csRepo.findStudentsByCPT(cpt.getCptId());
                fullDto.students = new ArrayList<>();
                for(CourseStudent cs : courseStudents){
                    student = SvcUtil.unwrapUser(userRepo.findById(cs.getStudentId()), cs.getStudentId());
                    fullDto.students.add(new SMUserDTO(student));
                }
            result.add(fullDto);
        }
        return result;
    }

    @Override
    public List<SMStudentDetailDTO> getStudentsByGrade(String gradeLevel){
        List<SMStudentDetailDTO> result = new ArrayList<>();
        List<SMUserDTO> students = SvcUtil.convertListUsers(userRepo.getStudentsByGradeLevel(gradeLevel));
        for(SMUserDTO student : students){
            result.add(buildStudentDetailDTO(student));
        }
        return result;
    }

    @Override
    public List<SMStudentDetailDTO> getStudentsNotAssignedToTeacher(){
        List<SMStudentDetailDTO> result = new ArrayList<>();
        List<SMUserDTO> students = SvcUtil.convertListUsers((List<User>)userRepo.getStudentsNotAssignedToTeacher());
        for(SMUserDTO student : students){
            result.add(buildStudentDetailDTO(student));
        }
        return result;
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
        SMCourseDTO dto = new SMCourseDTO();

        // dto.studentIds = new ArrayList<>(Arrays.asList(404l));
        // dto.cptIds = new ArrayList<>(Arrays.asList(15l,33l,51l,76l,58l,97l,115l,134l,188l));
        // dto.cptIds.remove(0);//remove the first entry to test delete
        // dto.cptIds.add(999l);//add a nonexistent entry to test add
        // dto.cptIds.add(1000l);//add a nonexistent entry to test add
        // dto.cptIds.add(1001l);//add a nonexistent entry to test add
        dto.studentIds = new ArrayList<>(Arrays.asList(354l,496l,103l,315l,311l,159l,320l,238l));
        dto.cptIds = new ArrayList<>(Arrays.asList(6l));

        /*
         * By studentId, need to compare the incoming studentId/cptId pairs against
         * the mock data coming from the db; 
         *      if an incoming cs exists in the db, do nothing
         *      if an incoming cs does not exist in db, add to a new 'add' list
         *      if a mock cs is not in the incoming list, add to a new 'delete' list
         */
        CourseStudent workingCs;
        List<CourseStudent> working = new ArrayList<>();
        List<CourseStudent> existing = new ArrayList<>();
        List<CourseStudent> addList = new ArrayList<>();
        List<CourseStudent> deleteList = new ArrayList<>();
        for(Long studentId : dto.studentIds){
            addList = new ArrayList<>();
            deleteList = new ArrayList<>();
            working = new ArrayList<>();
            existing = csRepo.findByStudentId(studentId);
            for(Long cptId : dto.cptIds){
                workingCs = new CourseStudent();
                workingCs.setStudentId(studentId);
                workingCs.setCptId(cptId);
                working.add(workingCs);
                if(!existing.contains(workingCs)){
                    addList.add(workingCs);
                }
            }
            for(CourseStudent cs : existing){
                if(!working.contains(cs)){
                    deleteList.add(cs);
                }
            }
            System.out.println("Existing: " + existing.size());
            // loop through working and add those to the database
            System.out.println("Working: " + working.size());
            // loop through add and add those to the database
            // loop through delete and delete those from the database
            System.out.println("Delete: " + deleteList.size());
            for(CourseStudent delCs : deleteList){
                csRepo.delete(delCs);
            }
            System.out.println("Add: " + addList.size());
            for(CourseStudent addCs : addList){
                csRepo.save(addCs);
            }
            
            // result = csRepo.findByStudentId(studentId);
            result.addAll(working);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
        // return new ResponseEntity<>(delete,HttpStatus.OK);
    }
}
