package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.dto.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMStudentListDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMUserDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.GradeEntry;

public interface TeacherSvc {
    List<Assignment> getAssignmentsByTeacherId(Long id);
    // SMReqDTO getAssignmentsByTeacherId(Long id);
    // List<Assignment> getAssignmentsByTeacherIdAndStudentId(Long teacher_id, Long student_id);
    List<SMStudentListDTO> getStudentsByTeacherId(Long teacherId);
    List<Assignment> saveAssignment(SMReqDTO dto);
    GradeEntry saveGradeEntry(SMReqDTO dto);
}
