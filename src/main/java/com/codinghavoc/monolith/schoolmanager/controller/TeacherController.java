package com.codinghavoc.monolith.schoolmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codinghavoc.monolith.schoolmanager.dto.GradeBookSummaryDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMGradeBookDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMGradeDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMSingleGradeDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMStudentListDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.GradeEntry;
import com.codinghavoc.monolith.schoolmanager.service.TeacherSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private TeacherSvc teacherSvc;
    
    @GetMapping("/getAssignments/teacher_id/{teacher_id}")
    public ResponseEntity<List<Assignment>> getAssignmentsByTeacher(@PathVariable Long teacher_id){
        return new ResponseEntity<>(teacherSvc.getAssignmentsByTeacherId(teacher_id), HttpStatus.OK);
    }

    @GetMapping("/getGradeBook/{teacher_id}")
    public ResponseEntity<SMGradeBookDTO> getGradeBook(@PathVariable Long teacher_id){
        return new ResponseEntity<>(teacherSvc.buildGradeBook(teacher_id),HttpStatus.OK);
    }

    @GetMapping("/getGradeSummaries/{teacher_id}")
    public ResponseEntity<List<GradeBookSummaryDTO>> getGradeSummaries(@PathVariable Long teacher_id){
        return new ResponseEntity<>(teacherSvc.getGradeBookSummaries(teacher_id),HttpStatus.OK);
    }

    @GetMapping("/getGradeEntries")
    public ResponseEntity<List<GradeEntry>> getGradeEntries(){
        return new ResponseEntity<>(teacherSvc.getGradeEntries(),HttpStatus.OK);
    }

    //will need another getAssignments EP with teacher and student id
    //TODO something along the lines of get grades by student and teacher ids
    // @GetMapping("/getAssignments/teacher_id/{teacher_id}/student_id/{student_id}")
    // public ResponseEntity<List<Assignment>> getAssignmentsByTeacher(@PathVariable Long teacher_id, @PathVariable Long student_id){
    //     return new ResponseEntity<>(teacherSvc.getAssignmentsByTeacherIdAndStudentId(teacher_id), HttpStatus.OK);
    // }

    @GetMapping("/getStudents/{teacher_id}")
    public ResponseEntity<List<SMStudentListDTO>>getStudentsByTeacherId(@PathVariable Long teacher_id){
        return new ResponseEntity<List<SMStudentListDTO>>(teacherSvc.getStudentsByTeacherId(teacher_id),HttpStatus.OK);
    }

    @PostMapping("/saveGrade")
    public ResponseEntity<List<GradeEntry>> saveGrade(@RequestBody List<SMGradeDTO> dto){
        return new ResponseEntity<>(teacherSvc.saveGradeEntry(dto), HttpStatus.OK);
    }

    @PostMapping("/saveNewAssignment")//tested, works, Save New Assignment
    public ResponseEntity<List<Assignment>> saveNewAssignment(@RequestBody SMReqDTO dto){
        return new ResponseEntity<>(teacherSvc.saveAssignment(dto), HttpStatus.OK);
    }

    @PostMapping("/updateGradeEntries")
    public ResponseEntity<List<GradeEntry>> updateGradeEntries(@RequestBody List<SMSingleGradeDTO> dtos){
        return new ResponseEntity<>(teacherSvc.updateGradeEntries(dtos), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<SMGradeBookDTO> test(){
        return new ResponseEntity<>(teacherSvc.test(38l),HttpStatus.OK);
    }
}
