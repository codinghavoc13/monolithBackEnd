package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.dto.SMGradeDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMSingleGradeDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMStudentListDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.GradeEntry;
import com.codinghavoc.monolith.schoolmanager.entity.StudentCompletedCourse;

public interface TeacherSvc {
    List<Assignment> getAssignmentsByTeacherId(Long id);
    // SMReqDTO getAssignmentsByTeacherId(Long id);
    // List<Assignment> getAssignmentsByTeacherIdAndStudentId(Long teacher_id, Long student_id);
    List<GradeEntry> getGradeEntries();
    List<SMStudentListDTO> getStudentsByTeacherId(Long teacherId);
    List<Assignment> saveAssignment(SMReqDTO dto);
    GradeEntry saveGradeEntry(SMGradeDTO dto);
    StudentCompletedCourse saveStudentCompletedCourse(Long studentId);
    List<GradeEntry> updateGradeEntries(List<SMSingleGradeDTO> dtos);
}
