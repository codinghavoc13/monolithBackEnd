package com.codinghavoc.monolith.schoolmanager.service;

import java.util.List;

import com.codinghavoc.monolith.schoolmanager.dto.SMGradeBookDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMGradeDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMSingleGradeDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMStudentListDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.GradeEntry;
import com.codinghavoc.monolith.schoolmanager.entity.StudentCompletedCourse;

public interface TeacherSvc {
    SMGradeBookDTO buildGradeBook(Long teacherId);
    List<Assignment> getAssignmentsByTeacherId(Long id);
    // SMReqDTO getAssignmentsByTeacherId(Long id);
    // List<Assignment> getAssignmentsByTeacherIdAndStudentId(Long teacher_id, Long student_id);
    List<GradeEntry> getGradeEntries();
    List<SMStudentListDTO> getStudentsByTeacherId(Long teacherId);
    List<Assignment> saveAssignment(SMReqDTO dto);
    List<GradeEntry> saveGradeEntry(List<SMGradeDTO> dtos);
    StudentCompletedCourse saveStudentCompletedCourse(Long studentId);
    List<GradeEntry> updateGradeEntries(List<SMSingleGradeDTO> dtos);
    SMGradeBookDTO test(Long teacherId);
}
