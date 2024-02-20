package com.codinghavoc.monolith.schoolmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.schoolmanager.dto.SMGradeBookDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMGradeDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMIndividualGradeDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMSingleGradeDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMStudentListDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMUserDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.Course;
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
    public List<SMGradeBookDTO> buildGradeBook(Long teacherId){
        ArrayList<SMGradeBookDTO> result = new ArrayList<>();
        return result;
    }

    @Override
    public List<Assignment> getAssignmentsByTeacherId(Long teacher_Id){
        return (List<Assignment>) assignmentRepo.findAllAssignmentByTeacherId(teacher_Id);
    }

    @Override
    public List<GradeEntry> getGradeEntries(){
        ArrayList<GradeEntry> result = new ArrayList<>();
        result = (ArrayList<GradeEntry>) geRepo.findAll();
        return result;
    }

    @SuppressWarnings("null")
    @Override
    public List<SMStudentListDTO> getStudentsByTeacherId(Long teacherId){
        ArrayList<SMStudentListDTO> result = new ArrayList<>();
        SMStudentListDTO working;
        List<CoursePeriodTeacher> cpts = cptRepo.findByTeacher(teacherId);
        List<CourseStudent> courseStudents;
        User student;
        for(CoursePeriodTeacher cpt : cpts){
            working = new SMStudentListDTO();
            working.cptId = cpt.getCptId();
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

    /*This is used to create the bulk, blank grade fields that will allow the build of the grade book */
    @Override
    public List<GradeEntry> saveGradeEntry(List<SMGradeDTO> dtos){
        ArrayList<GradeEntry> result = new ArrayList<>();
        GradeEntry check;
        for(SMGradeDTO dto : dtos){
            check = geRepo.findByStudentAndAssignmentId(dto.studentId, dto.assignmentId);
            // if(check!=null){
            //     // result.add(check);
            // } else {
            if(check==null){
                GradeEntry ge = new GradeEntry(dto.cptId, dto.studentId, dto.assignmentId, dto.grade);
                result.add(geRepo.save(ge));
            }
        }
        return result;
    }

    public User getStaffMember(Long id) {
        return SvcUtil.unwrapUser(userRepo.findById(id),id);
    }

    @Override
    public StudentCompletedCourse saveStudentCompletedCourse(Long studentId){
        StudentCompletedCourse scc = new StudentCompletedCourse();
        scc.setStudentId(studentId);
        return scc;
    }

    @SuppressWarnings("null")
    @Override
    public List<GradeEntry> updateGradeEntries(List<SMSingleGradeDTO> dtos){
        ArrayList<GradeEntry> result = new ArrayList<>();
        GradeEntry ge;
        for(SMSingleGradeDTO dto : dtos){
            ge = SvcUtil.unwrapGradeEntry(geRepo.findById(dto.gradeId), dto.gradeId);
            ge.setGrade(dto.grade);
            result.add(geRepo.save(ge));
        }
        return result;
    }

    @Override
    public SMGradeBookDTO test(Long teacherId){
        SMGradeBookDTO result = new SMGradeBookDTO();
        Course course;
        Optional<CoursePeriodTeacher> cpt;
        User student;
        Assignment assignment;
        SMIndividualGradeDTO dto;
        List<GradeEntry> gradeEntries = (List<GradeEntry>) geRepo.findAll();
        for(GradeEntry ge : gradeEntries){
            dto = new SMIndividualGradeDTO();
            dto.grade = ge.getGrade();
            assignment = SvcUtil.unwrapAssignment(assignmentRepo.findById(ge.getAssignmentId()), ge.getAssignmentId());
            dto.assignmentDueDate = assignment.getAssignmentDueDate();
            result.addWeeksListEntry(dto.assignmentDueDate);
            dto.assignmentTitle = assignment.getAssignmentTitle();
            dto.assignmentType = assignment.getAssignmentType().value;
            result.addAssignmentType(dto.assignmentType);
            student = SvcUtil.unwrapUser(userRepo.findById(ge.getStudentId()), ge.getStudentId());
            dto.studentFirstName = student.getFirstName();
            dto.studentLastName = student.getLastName();
            cpt = cptRepo.findById(ge.getCptId());
            dto.period = cpt.get().getPeriod();
            result.addPeriod(dto.period);
            course = SvcUtil.unwrapCourse(courseRepo.findById(cpt.get().getCourseId()), cpt.get().getCourseId());
            dto.courseName = course.getCourseName();
            result.addCourseName(dto.courseName);
            result.gradeDtos.add(dto);
        } 
        return result;
    }
}
