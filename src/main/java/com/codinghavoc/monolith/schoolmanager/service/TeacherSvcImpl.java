package com.codinghavoc.monolith.schoolmanager.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.schoolmanager.dto.GradeBookSummaryDTO;
import com.codinghavoc.monolith.schoolmanager.dto.IndividualGradeSummaryDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMGradeBookDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMGradeDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMIndividualGradeDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMSingleGradeDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMStudentListDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMUserDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.ConfigEntry;
import com.codinghavoc.monolith.schoolmanager.entity.Course;
import com.codinghavoc.monolith.schoolmanager.entity.CoursePeriodTeacher;
import com.codinghavoc.monolith.schoolmanager.entity.CourseStudent;
import com.codinghavoc.monolith.schoolmanager.entity.GradeEntry;
import com.codinghavoc.monolith.schoolmanager.entity.StudentCompletedCourse;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.enums.AssignmentType;
import com.codinghavoc.monolith.schoolmanager.enums.CourseBlock;
import com.codinghavoc.monolith.schoolmanager.repo.AssignmentRepo;
import com.codinghavoc.monolith.schoolmanager.repo.CPTRepo;
import com.codinghavoc.monolith.schoolmanager.repo.ConfigRepo;
import com.codinghavoc.monolith.schoolmanager.repo.CourseRepo;
import com.codinghavoc.monolith.schoolmanager.repo.CourseStudentRepo;
import com.codinghavoc.monolith.schoolmanager.repo.GradeEntryRepo;
import com.codinghavoc.monolith.schoolmanager.repo.UserRepo;
import com.codinghavoc.monolith.schoolmanager.util.SvcUtil;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@SuppressWarnings("null")
@AllArgsConstructor
@Service
public class TeacherSvcImpl implements TeacherSvc {
    AssignmentRepo assignmentRepo;
    ConfigRepo configRepo;
    CourseRepo courseRepo;
    CourseStudentRepo csRepo;
    CPTRepo cptRepo;
    GradeEntryRepo geRepo;
    UserRepo userRepo;

    ArrayList<Double> homeworkGrades = new ArrayList<>();
    ArrayList<Double> quizGrades = new ArrayList<>();
    ArrayList<Double> reportGrades = new ArrayList<>();
    ArrayList<Double> testGrades = new ArrayList<>();

    @Override
    public SMGradeBookDTO buildGradeBook(Long teacherId){
        SMGradeBookDTO result = new SMGradeBookDTO();
        Course course;
        Optional<CoursePeriodTeacher> cptOpt;
        User student;
        Assignment assignment;
        SMIndividualGradeDTO dto;
        String courseInfo;
        //need to update this to get all cptids associated with the given teacher id
        List<CoursePeriodTeacher> cpts = (List<CoursePeriodTeacher>)cptRepo.findByTeacher(teacherId);
        List<Long> cptList = new ArrayList<>();
        for(CoursePeriodTeacher cpt : cpts){
            cptList.add(cpt.getCptId());
        }
        List<GradeEntry> gradeEntries = (List<GradeEntry>) geRepo.findByCptIdIn(cptList);
        for(GradeEntry ge : gradeEntries){
            dto = new SMIndividualGradeDTO();
            dto.grade = ge.getGrade();
            dto.gradeId = ge.getGradeId();
            assignment = SvcUtil.unwrapAssignment(assignmentRepo.findById(ge.getAssignmentId()), ge.getAssignmentId());
            dto.assignmentDueDate = assignment.getAssignmentDueDate();
            result.addWeeksListEntry(dto.assignmentDueDate);
            dto.assignmentTitle = assignment.getAssignmentTitle();
            dto.assignmentType = assignment.getAssignmentType().value;
            result.addAssignmentType(dto.assignmentType);
            student = SvcUtil.unwrapUser(userRepo.findById(ge.getStudentId()), ge.getStudentId());
            dto.studentFirstName = student.getFirstName();
            dto.studentLastName = student.getLastName();
            cptOpt = cptRepo.findById(ge.getCptId());
            dto.period = cptOpt.get().getPeriod();
            result.addPeriod(dto.period);
            course = SvcUtil.unwrapCourse(courseRepo.findById(cptOpt.get().getCourseId()), cptOpt.get().getCourseId());
            // dto.courseName = course.getCourseName();
            courseInfo = course.getCourseName();
            courseInfo += " - " + dto.period;
            if(course.getCourseBlock()!= CourseBlock.FULL_YEAR){
                courseInfo += " - " + course.getCourseBlock().value;
            }
            dto.courseName = courseInfo;
            result.addCourseName(courseInfo);
            result.gradeDtos.add(dto);
        } 
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

    @Override
    public List<GradeBookSummaryDTO> getGradeBookSummaries(Long teacherId){
        ArrayList<GradeBookSummaryDTO> result = new ArrayList<>();
        IndividualGradeSummaryDTO igsdto;
        Course course;
        GradeBookSummaryDTO gbsdto;
        List<User> students;
        List<GradeEntry> grades;
        //get a list of cptids by teacherId from cptrepo
        List<CoursePeriodTeacher> cpts = (List<CoursePeriodTeacher>)cptRepo.findByTeacher(teacherId);
        for(CoursePeriodTeacher cpt : cpts){
            //build out the gbsdto, reset grades lists to new
            //declare four arraylists, one for each assignment type
            gbsdto = new GradeBookSummaryDTO();
            gbsdto.studentInfo = new ArrayList<>();
            gbsdto.cptId = cpt.getCptId();
            course = courseRepo.findById(cpt.getCourseId()).get();
            gbsdto.courseName = course.getCourseName();
            gbsdto.courseBlock = course.getCourseBlock().value;
            gbsdto.period = cpt.getPeriod();
            students = new ArrayList<>();
            //for each cptid, get a list of studentids by cptid from csrepo
            for(CourseStudent cs : csRepo.findStudentsByCPT(cpt.getCptId())){
                students.add(userRepo.findById(cs.getStudentId()).get());
            }
            //for each studentid, get a list of gradeentries from gerepo
            for(User student : students){
                homeworkGrades = new ArrayList<>();
                quizGrades = new ArrayList<>();
                reportGrades = new ArrayList<>();
                testGrades = new ArrayList<>();
                igsdto = new IndividualGradeSummaryDTO();
                igsdto.studentFirstName = student.getFirstName();
                igsdto.studentLastName = student.getLastName();
                gbsdto.studentInfo.add(igsdto);
                grades = geRepo.findByStudentId(student.getUserId());
                //for each gradeentry, get the assignment type of each
                //will look at moving this out of here and into a standalone method for use here and in saveStudentCompletedCourse
                parseGradeEntries(grades);
                // for(GradeEntry ge : grades){
                //     /*
                //      * Add a check to only process the ge if ge.grade is gte 0
                //      */
                //     if(ge.getGrade()>=0){
                //         assignmentType = assignmentRepo.findById(ge.getAssignmentId()).get().getAssignmentType();
                //         if (assignmentType.value.equals("Homework")) homeworkGrades.add(ge.getGrade());
                //         if (assignmentType.value.equals("Quiz")) quizGrades.add(ge.getGrade());
                //         if (assignmentType.value.equals("Report")) reportGrades.add(ge.getGrade());
                //         if (assignmentType.value.equals("Test")) testGrades.add(ge.getGrade());
                //     }
                // }
                //loop over each grade entry and split into grades lists above
                //send each list through calcGradeAvg and save in gbsdto
                igsdto.homeworkAvg = calcGradeAvg(homeworkGrades);
                igsdto.quizAvg = calcGradeAvg(quizGrades);
                igsdto.reportAvg = calcGradeAvg(reportGrades);
                igsdto.testAvg = calcGradeAvg(testGrades);
                /*
                 * Something to think about: when no grades have been submitted for some fields, the percent 
                 * break for those types will still apply, massively skewing the overall avg. Example, as of
                 * 20240229, I have no reports in the database and reports are set to be 25% of the overall
                 * grade; because of this, a student will 100s on all other assignments but no reports submitted
                 * will end up with a overall grade of 75.
                 */
                double overallGrade = 0.0;
                if(igsdto.homeworkAvg>=0.0){
                    overallGrade += igsdto.homeworkAvg * getAssignmentTypePercentage("homework_percent");
                }
                if(igsdto.quizAvg>=0.0){
                    overallGrade += igsdto.quizAvg * getAssignmentTypePercentage("quiz_percent");
                }
                if(igsdto.reportAvg>=0.0){
                    overallGrade += igsdto.reportAvg * getAssignmentTypePercentage("report_percent");
                }
                if(igsdto.testAvg>=0.0){
                    overallGrade += igsdto.testAvg * getAssignmentTypePercentage("test_percent");
                }
                igsdto.overallAvg = overallGrade;
            }
            result.add(gbsdto);
        }
        return result;
    }

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
    public StudentCompletedCourse saveStudentCompletedCourse(Long studentId, Long cptId){
        StudentCompletedCourse scc = new StudentCompletedCourse();
        scc.setStudentId(studentId);
        scc.setCourseId(cptRepo.findById(cptId).get().getCourseId());
        scc.setCompletedDate(LocalDate.now());
        ArrayList<GradeEntry> grades = (ArrayList<GradeEntry>) geRepo.findByStudentIdCptId(studentId, cptId);
        parseGradeEntries(grades);
        System.out.println("Homework: " + homeworkGrades.size());
        System.out.println("Quiz: " + quizGrades.size());
        System.out.println("Report: " + reportGrades.size());
        System.out.println("Test: " + testGrades.size());
        return scc;
    }

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

    private Double calcGradeAvg(ArrayList<Double> grades){
        if(grades.size() == 0){
            return 0.0;
        }
        double result = 0.0;
        for(double grade : grades){
            result+=grade;
        }
        if(result == 0.0){
            return result;
        }
        result /= grades.size();
        return result;
    }

    private Double getAssignmentTypePercentage(String type){
        ConfigEntry ce = configRepo.getGradeCalcPercentage(type);
        if(ce != null){
            return Double.valueOf(ce.value);
        } else {
            return 0.25;
        }
    }

    private void parseGradeEntries(List<GradeEntry> grades){
        AssignmentType assignmentType;
        for(GradeEntry ge : grades){
            if(ge.getGrade()>=0){
                assignmentType = assignmentRepo.findById(ge.getAssignmentId()).get().getAssignmentType();
                if (assignmentType.value.equals("Homework")) homeworkGrades.add(ge.getGrade());
                if (assignmentType.value.equals("Quiz")) quizGrades.add(ge.getGrade());
                if (assignmentType.value.equals("Report")) reportGrades.add(ge.getGrade());
                if (assignmentType.value.equals("Test")) testGrades.add(ge.getGrade());
            }
        }
    }

    @Override
    public SMGradeBookDTO test(Long teacherId){
        SMGradeBookDTO result = new SMGradeBookDTO();
        return result;
    }
}
