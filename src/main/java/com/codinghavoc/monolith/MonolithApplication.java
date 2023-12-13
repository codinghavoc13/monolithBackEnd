package com.codinghavoc.monolith;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@SpringBootApplication
public class MonolithApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(MonolithApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Saving staff can now be done with the controller
		// Staff staff1 = new Staff("Ray","Mayer",Role.ADMIN);
		// staffSvc.saveStaff(staff1);

		// Staff[] teachers = new Staff[]{
		// 	new Staff("Delores","Lang",Role.TEACHER),
		// 	new Staff("Gary","Bernard",Role.TEACHER)
		// };
	}

}
