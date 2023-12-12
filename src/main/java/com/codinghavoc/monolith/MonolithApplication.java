package com.codinghavoc.monolith;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.codinghavoc.monolith.schoolmanager.entity.Assignment;
import com.codinghavoc.monolith.schoolmanager.entity.Staff;
import com.codinghavoc.monolith.schoolmanager.entity.Student;
import com.codinghavoc.monolith.schoolmanager.enums.AssignmentType;
import com.codinghavoc.monolith.schoolmanager.enums.Role;
import com.codinghavoc.monolith.schoolmanager.service.StaffSvc;
import com.codinghavoc.monolith.schoolmanager.service.StudentSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@SpringBootApplication
public class MonolithApplication implements CommandLineRunner{
	private StudentSvc studentSvc;
	private StaffSvc staffSvc;

	public static void main(String[] args) {
		SpringApplication.run(MonolithApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Staff staff1 = new Staff("Ray","Mayer",Role.ADMIN);
		staffSvc.saveStaff(staff1);

		Staff[] teachers = new Staff[]{
			new Staff("Delores","Lang",Role.TEACHER),
			new Staff("Gary","Bernard",Role.TEACHER)
		};

		for(Staff s : teachers){
			staffSvc.saveStaff(s);
		}

		Student[] students = new Student[]{
			new Student("abc111", "Ronald", "Watts",Role.STUDENT, "3"),
			new Student("abc112", "Alice", "Walker", Role.STUDENT, "3"),
			new Student("abc113", "David", "Jefferson", Role.STUDENT, "3"),
			new Student("abc114", "Phyllis", "Pittman", Role.STUDENT, "3"),
			new Student("abc115", "James", "Wiley", Role.STUDENT, "3"),
			new Student("abc116", "Shirley", "Powell", Role.STUDENT, "3"),
			new Student("abc123","Calvin","Adams",Role.STUDENT,"3"),
			new Student("abc123","Lorraine","McCullough",Role.STUDENT,"3"),
			new Student("abc123","Howard","Wilkins",Role.STUDENT,"3"),
			new Student("abc123","Heather","Battle",Role.STUDENT,"3"),
			new Student("abc123","Erick","Chase",Role.STUDENT,"3"),
			new Student("abc123","Tasha","Jensen",Role.STUDENT,"3")
		};
		
		for(Student s : students){
			studentSvc.saveStudent(s);
		}

		for(int i = 0; i < students.length; i++){
			staffSvc.addStudentToTeacherRoster(teachers[i%2].getStaff_id(), students[i].getStudent_id());
		}

		Assignment[] assignments = new Assignment[]{
			new Assignment("Homework 1", AssignmentType.HOMEWORK),
			new Assignment("Homework 2", AssignmentType.HOMEWORK),
			new Assignment("Homework 3", AssignmentType.HOMEWORK),
			new Assignment("Homework 4", AssignmentType.HOMEWORK),
			new Assignment("Quiz 1", AssignmentType.QUIZ),
			new Assignment("Test 1", AssignmentType.TEST),
			new Assignment("Homework 1", AssignmentType.HOMEWORK),
			new Assignment("Homework 2", AssignmentType.HOMEWORK),
			new Assignment("Homework 3", AssignmentType.HOMEWORK),
			new Assignment("Quiz 1", AssignmentType.QUIZ),
			new Assignment("Test 1", AssignmentType.TEST)
		};

		int i = 0;
		Long l = 2l;
		for(Assignment a : assignments){
			staffSvc.saveAssignment(a,l);
			if(i>=4)l = 3l;
			i++;
		}
	}

}
