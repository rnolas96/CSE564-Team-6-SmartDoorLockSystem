package com.smartdoor.project;

import com.smartdoor.models.Barcode;
import com.smartdoor.models.Fingerprint;
import com.smartdoor.services.sensors.FingerprintScanner;
import com.smartdoor.services.sensors.RFIDScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.smartdoor.services.CentralManagementSystem;

@SpringBootApplication
public class ProjectApplication {

	public static void main(final String[] args) throws Exception {

		SpringApplication.run(ProjectApplication.class, args);

		// Start each sensor. Extend Sensor to Thread

		String fingerPrintFeatureset = "1001011";

	 	String faceRecognitionFeatureset = "1010101";

	 	String rfidScan = "1010011";

		// testing the fingerprint scanner with hardcoded input
		FingerprintScanner fp = new FingerprintScanner();
		Fingerprint scan = new Fingerprint();
		scan.value=  "fingerPrintUser1";
		System.out.println("response "+fp.biometricProcessor(false, scan, 1));

		// testing the rfid scanner with hardcoded input
		RFIDScanner rfid = new RFIDScanner();
		Barcode rfidscan = new Barcode();
		rfidscan.value = "RFIDUser1";
		System.out.println("response "+rfid.RFIDProcessor(false, rfidscan, 1));

		final CentralManagementSystem centralManagementSystem = new CentralManagementSystem(fingerPrintFeatureset, faceRecognitionFeatureset, rfidScan);
		centralManagementSystem.start();

	}

}
