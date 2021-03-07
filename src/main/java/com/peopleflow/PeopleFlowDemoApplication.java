package com.peopleflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PeopleFlowDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeopleFlowDemoApplication.class, args);
	}

}


//	CREATE TABLE `new_schema`.`peopleflow_user` (
//		`id` INT NOT NULL AUTO_INCREMENT,
//		`username` VARCHAR(45) NULL,
//		`full_name` VARCHAR(45) NULL,
//		`user_state` VARCHAR(45) NULL,
//		PRIMARY KEY (`id`));

