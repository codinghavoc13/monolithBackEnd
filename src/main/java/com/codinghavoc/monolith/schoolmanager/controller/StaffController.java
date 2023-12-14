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

import com.codinghavoc.monolith.schoolmanager.dto.SMLoginDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMRespDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Staff;
import com.codinghavoc.monolith.schoolmanager.service.StaffSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/staff")
public class StaffController {
    private StaffSvc staffSvc;
    
    @GetMapping("/all")
    public ResponseEntity<SMRespDTO> getAllStaff(){
        return new ResponseEntity<>(staffSvc.getAllStaff(),HttpStatus.OK);
    }

    @GetMapping("/getAssignments/{teacher_id}")
    public ResponseEntity<SMRespDTO> getAssignmentsByTeacher(@PathVariable Long teacher_id){
        return new ResponseEntity<>(staffSvc.getAssignmentsByTeacherId(teacher_id), HttpStatus.OK);
    }

    @GetMapping("/getStudents/{teacher_id}")
    public ResponseEntity<SMRespDTO>getStudentsAssignedToTeacher(@PathVariable Long teacher_id){
        return new ResponseEntity<>(staffSvc.getStudentsAssignedToTeacher(teacher_id),HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<SMRespDTO>login(@RequestBody SMLoginDTO dto){
        return new ResponseEntity<>(staffSvc.login(dto), HttpStatus.OK);
    }

    @PostMapping("/saveGrade")
    public ResponseEntity<SMRespDTO> saveGrade(@RequestBody SMReqDTO dto){
        return new ResponseEntity<>(staffSvc.saveGradeEntry(dto), HttpStatus.OK);
    }

    @PostMapping("/saveNewAssignment")
    public ResponseEntity<SMRespDTO> saveNewAssignment(@RequestBody SMReqDTO dto){
        return new ResponseEntity<>(staffSvc.saveAssignment(dto), HttpStatus.CREATED);
    }

    @PostMapping("/saveNewStaff")
    public ResponseEntity<SMRespDTO> saveNewStaff(@RequestBody List<SMRegisterDTO> dtos){
        return new ResponseEntity<>(staffSvc.saveStaff(dtos), HttpStatus.CREATED);
    }

    @PostMapping("/saveNewStudent")
    public ResponseEntity<SMRespDTO> saveNewStudent(@RequestBody SMReqDTO dto){
        return new ResponseEntity<>(staffSvc.saveStudent(dto.students), HttpStatus.CREATED);
    }
}
