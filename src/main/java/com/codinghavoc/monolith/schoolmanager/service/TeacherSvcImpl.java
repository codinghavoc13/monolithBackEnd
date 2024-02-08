package com.codinghavoc.monolith.schoolmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.schoolmanager.dto.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMStudentListDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMUserDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.CoursePeriodTeacher;
import com.codinghavoc.monolith.schoolmanager.entity.CourseStudent;
import com.codinghavoc.monolith.schoolmanager.entity.GradeEntry;
import com.codinghavoc.monolith.schoolmanager.entity.StudentCompletedCourse;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.repo.AssignmentRepo;
import com.codinghavoc.monolith.schoolmanager.repo.CPTRepo;
import com.codinghavoc.monolith.schoolmanager.repo.CourseRepo;
import com.codinghavoc.monolith.schoolmanager.repo.CourseStudentRepo;
import com.codinghavoc.monolith.schoolmanager.repo.GradeEntryRepo;
import com.codinghavoc.monolith.schoolmanager.repo.UserRepo;
import com.codinghavoc.monolith.schoolmanager.util.SvcUtil;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TeacherSvcImpl implements TeacherSvc {
    AssignmentRepo assignmentRepo;
    CourseRepo courseRepo;
    CourseStudentRepo csRepo;
    CPTRepo cptRepo;
    GradeEntryRepo geRepo;
    UserRepo userRepo;

    @Override
    public List<Assignment> getAssignmentsByTeacherId(Long teacher_Id){
        return (List<Assignment>) assignmentRepo.findAllAssignmentByTeacherId(teacher_Id);
    }

    @Override
    public List<SMStudentListDTO> getStudentsByTeacherId(Long teacherId){
        ArrayList<SMStudentListDTO> result = new ArrayList<>();
        SMStudentListDTO working;
        /*
        TODO Here and everywhere else that courseRepo is called to get course information
        is going to have to be reworked to use or include the ctpRepo
        */
        /*
         * Need to get a list of CoursePeriodTeacher objects based on the teacherId
         */
        List<CoursePeriodTeacher> cpts = cptRepo.findByTeacher(teacherId);
        /*
         * Loop over the CPT objects, getting students from CourseStudent that have
         * a matching cptId
         */
        List<CourseStudent> courseStudents;
        User student;
        for(CoursePeriodTeacher cpt : cpts){
            working = new SMStudentListDTO();
            working.period = cpt.getPeriod();
            working.course = SvcUtil.unwrapCourse(courseRepo.findById(cpt.getCourseId()),cpt.getCourseId());
            working.students = new ArrayList<>();
            courseStudents = csRepo.findStudentsByCPT(cpt.getCptId());
            if(courseStudents.size()>0){
                for(CourseStudent cs : courseStudents){
                    student = SvcUtil.unwrapUser(userRepo.findById(cs.getStudentId()), cs.getStudentId());
                    working.students.add(new SMUserDTO(student));
                }
            }
            result.add(working);
        }
        return result;
    }
    

    @Transactional
    @Override
    public List<Assignment> saveAssignment(SMReqDTO dto){
        List<Assignment> result = new ArrayList<>();
        List<Assignment> assignmentDtos = dto.assignments;
        User staff = getStaffMember(dto.teacherId);
        for(Assignment a : assignmentDtos){
            a.setTeacherId(staff.getUserId());
            result.add(assignmentRepo.save(a));
        }
        return result;
    }

    @Override
    public GradeEntry saveGradeEntry(SMReqDTO dto){
        GradeEntry check = geRepo.findByStudentAndAssignmentId(dto.studentId, dto.assignmentId);
        if(check!=null){
            return check;
        }
        GradeEntry ge = new GradeEntry(dto.courseId,dto.studentId,dto.teacherId, dto.assignmentId, dto.grade);
        return geRepo.save(ge);
    }

    public User getStaffMember(Long id) {
        return SvcUtil.unwrapUser(userRepo.findById(id),id);
    }

    @Override
    public StudentCompletedCourse saveStudentCompletedCourse(Long studentId){
        StudentCompletedCourse scc = new StudentCompletedCourse();
        scc.setStudentId(studentId);
        //need to get 
        return scc;
    }
}
