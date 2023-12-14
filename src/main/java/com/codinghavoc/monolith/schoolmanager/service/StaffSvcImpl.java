package com.codinghavoc.monolith.schoolmanager.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codinghavoc.monolith.schoolmanager.dto.SMLoginDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMRegisterDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMReqDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMRespDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.GradeEntry;
import com.codinghavoc.monolith.schoolmanager.entity.Staff;
import com.codinghavoc.monolith.schoolmanager.entity.Student;
import com.codinghavoc.monolith.schoolmanager.exception.AssignmentNotFoundException;
import com.codinghavoc.monolith.schoolmanager.exception.StaffNotFoundException;
import com.codinghavoc.monolith.schoolmanager.exception.StudentNotFoundException;
import com.codinghavoc.monolith.schoolmanager.repo.AssignmentRepo;
import com.codinghavoc.monolith.schoolmanager.repo.GradeEntryRepo;
import com.codinghavoc.monolith.schoolmanager.repo.StaffRepo;
import com.codinghavoc.monolith.schoolmanager.repo.StudentRepo;
import com.codinghavoc.monolith.schoolmanager.util.PasswordHashUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StaffSvcImpl implements StaffSvc {
    AssignmentRepo assignmentRepo;
    GradeEntryRepo geRepo;
    StaffRepo staffRepo;
    StudentRepo studentRepo;

    @Transactional
    @Override
    public SMRespDTO addStudentToTeacherRoster(Long teacher_id, Long student_id) {
        Long check = staffRepo.checkForStudentTeacherEntry(teacher_id, student_id);
        if(check > 0) {
            return new SMRespDTO("Entry pair exists", null);
        } else {
            Staff staff = ((Staff)getStaffMember(teacher_id).body);
            Optional<Student> wrappedStudent = studentRepo.findById(student_id);
            Student unwrappedStudent = unwrapStudent(wrappedStudent, student_id);
            staff.getStudents().add(unwrappedStudent);
            return new SMRespDTO("Entry pair does not exist", null);
        }
    }

    @Override
    public SMRespDTO getAllStaff() {
        return new SMRespDTO("success", (List<Staff>)staffRepo.findAll());
    }

    @Override
    public SMRespDTO getAssignmentsByTeacherId(Long teacher_Id){
        return new SMRespDTO("success", (List<Assignment>) assignmentRepo.findAllAssignmentByStaffId(teacher_Id));
    }

    @Override
    public SMRespDTO getStaffMember(Long id) {
        return new SMRespDTO("success", unwrapStaff(staffRepo.findById(id),id));
    }
    
    @Override
    public SMRespDTO getStaffUsernames(){
        return new SMRespDTO("info", (List<String>)staffRepo.getUserNames());
    }

    @Override
    public SMRespDTO getStudentsAssignedToTeacher(Long teacher_id){
        return new SMRespDTO("success", (List<Student>)studentRepo.getStudentsByTeacherId(teacher_id));
    }

    @Override
    public SMRespDTO login(SMLoginDTO dto){
        Staff check = staffRepo.getStaffByUsername(dto.username);
        System.out.println(check);
        if(check==null) return new SMRespDTO("no user with that username", null);
        try {
            boolean valid = PasswordHashUtil.validateLogin(dto.password, check.getPasswordSalt(), check.getPasswordHash());
            System.out.println("valid: " + valid);
            if(valid) {
                return new SMRespDTO("success", check);
            } else {
                return new SMRespDTO("invalid login credentials", null);
            }
        } catch (NoSuchAlgorithmException e) {
            return new SMRespDTO("Error with hash algorithm", null);
        }
    }

    @Override
    public SMRespDTO saveAssignment(SMReqDTO dto){
        List<Assignment> result = new ArrayList<>();
        List<SMReqDTO> assignmentDtos = dto.assignments;
        Assignment temp;
        for(SMReqDTO d : assignmentDtos){
            temp = d.assignment;
            temp.setTeacher((Staff)(getStaffMember(d.staff_id).body));
            result.add(assignmentRepo.save(temp));
        }
        return new SMRespDTO("success", result);
    }

    @Override
    public SMRespDTO saveGradeEntry(SMReqDTO dto){
        GradeEntry check = geRepo.findByStudentAndAssignmentId(dto.student_id, dto.assignment_id);
        if(check!=null){
            return new SMRespDTO("That combination already exists",check);
        }
        GradeEntry ge = new GradeEntry(dto.staff_id,dto.student_id,dto.assignment_id, dto.grade);
        return new SMRespDTO("Added grade entry to database", geRepo.save(ge));
    }

    @Override
    public SMRespDTO saveStaff(List<SMRegisterDTO> dtos){
        System.out.println(dtos);
        ArrayList<Staff> result = new ArrayList<>();
        for(SMRegisterDTO dto : dtos){
            try {
                Staff staff = new Staff();
                staff.setFirstName(dto.firstName);
                staff.setLastName(dto.lastName);
                staff.setEmailString(dto.email);
                staff.setPhoneString(dto.phoneString);
                String[] pass;
                pass = PasswordHashUtil.hashPW(dto.password);
                staff.setPasswordSalt(pass[0]);
                staff.setPasswordHash(pass[1]);
                //build out the password salt and hash
                staff.setRole(dto.role);
                staff.setUsername(dto.username);
                result.add(staffRepo.save(staff));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return new SMRespDTO("response",result);
    }

    @Override
    public SMRespDTO saveStudent(List<Student> students){
        List<Student> result = new ArrayList<>();
        for(Student s : students){
            result.add(studentRepo.save(s));
        }
        return new SMRespDTO("created", result);

    }

    static Staff unwrapStaff(Optional<Staff> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new StaffNotFoundException(id);
    }

    static Student unwrapStudent(Optional<Student> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new StudentNotFoundException(id);
    }

    static Assignment unwrapAssignment(Optional<Assignment> entity, Long id){
        if(entity.isPresent()) return entity.get();
        else throw new AssignmentNotFoundException(id);
    }
    
}
