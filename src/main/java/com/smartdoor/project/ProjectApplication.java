package com.smartdoor.project;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.smartdoor.services.CentralManagementSystem;

@SpringBootApplication
public class ProjectApplication {

	public static void main(final String[] args) throws IOException{

		SpringApplication.run(ProjectApplication.class, args);

		// Start each sensor. Extend Sensor to Thread

		String fingerPrintFeatureset = "1001011";

	 	String faceRecognitionFeatureset = "1010101";

	 	String rfidScan = "1010011";

		final CentralManagementSystem centralManagementSystem = new CentralManagementSystem(fingerPrintFeatureset, faceRecognitionFeatureset, rfidScan);
		centralManagementSystem.start();

	}

}
