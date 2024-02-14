package com.codinghavoc.monolith.schoolmanager.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.codinghavoc.monolith.schoolmanager.dto.SMCourseDetailDTO;
import com.codinghavoc.monolith.schoolmanager.dto.SMUserDTO;
import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.Course;
import com.codinghavoc.monolith.schoolmanager.entity.GradeEntry;
import com.codinghavoc.monolith.schoolmanager.entity.User;
import com.codinghavoc.monolith.schoolmanager.exception.AssignmentNotFoundException;
import com.codinghavoc.monolith.schoolmanager.exception.CourseNotFoundException;
import com.codinghavoc.monolith.schoolmanager.exception.GradeEntryNotFoundException;
import com.codinghavoc.monolith.schoolmanager.exception.UserNotFoundException;

public class SvcUtil {
    public static SMCourseDetailDTO buildSmCourseDetailDTO(Course course, User teacher, int period, Long cptId){
        SMCourseDetailDTO result = new SMCourseDetailDTO();
        result.courseId = course.getCourseId();
        result.courseName = course.getCourseName();
        result.courseBlock = course.getCourseBlock();
        result.cptId = cptId;
        result.credit = course.getCredit();
        result.period = period;
        result.teacherFirstName = teacher.getFirstName();
        result.teacherLastName = teacher.getLastName();
        result.teacherId = teacher.getUserId();
        return result;
    }

    public static List<SMUserDTO> convertListUsers(List<User> users){
        List<SMUserDTO> result = new ArrayList<>();
        for(User user : users){
            result.add(new SMUserDTO(user));
        }
        return result;
    }

    public static String padString(String input){
        int length = 9;
        if(input.length() >= length){
            return input;
        }
        StringBuilder sb = new StringBuilder();
        while(sb.length() < (length - input.length())){
            sb.append('0');
        }
        sb.append(input);
        return sb.toString();
    }

    public static Assignment unwrapAssignment(Optional<Assignment> entity, Long id){
        if(entity.isPresent()) return entity.get();
        else throw new AssignmentNotFoundException(id);
    }

    public static Course unwrapCourse(Optional<Course> entity, Long id){
        if(entity.isPresent()) return entity.get();
        else throw new CourseNotFoundException(id);
    }

    public static GradeEntry unwrapGradeEntry(Optional<GradeEntry> entity, Long id){
        if(entity.isPresent()) return entity.get();
        else throw new GradeEntryNotFoundException(id);        
    }

    public static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new UserNotFoundException(id);
    }
}
