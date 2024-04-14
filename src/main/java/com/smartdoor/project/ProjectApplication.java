package com.smartdoor.project;

import com.smartdoor.models.Barcode;
import com.smartdoor.models.Fingerprint;
import com.smartdoor.services.sensors.FingerprintScanner;
import com.smartdoor.services.sensors.RFIDScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.smartdoor.services.CentralManagementSystem;
import com.smartdoor.services.InputService;

@SpringBootApplication
public class ProjectApplication {

	public static void main(final String[] args) throws Exception {

		SpringApplication.run(ProjectApplication.class, args);

		InputService inputService = new InputService();
		inputService.startSensorThreads();
		
	}

}
