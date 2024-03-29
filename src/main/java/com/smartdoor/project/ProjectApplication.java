package com.smartdoor.project;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.smartdoor.services.CentralManagementSystem;

@SpringBootApplication
public class ProjectApplication {

	public static void main(final String[] args) throws IOException{
		
		final CentralManagementSystem centralManagementSystem = new CentralManagementSystem();

		SpringApplication.run(ProjectApplication.class, args);
	}

}
