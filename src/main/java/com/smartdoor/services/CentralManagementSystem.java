package com.smartdoor.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.smartdoor.models.AdminControlSystemOutput;
import com.smartdoor.models.Notification;

public class CentralManagementSystem extends Thread {

	private Lock lock;

	private AdminControlSystem adminControlSystem = new AdminControlSystem();

	private CombinedVerificationSystem combinedVerificationSystem = new CombinedVerificationSystem();

	private String fingerPrintFeatureset;

	private String faceRecognitionFeatureset;

	private String rfidScan;

	private ArrayList<Boolean> configInput = new ArrayList<Boolean>() {{
		add(true);
		add(true);
		add(true);
	}};

	private String option = null;

	private UUID userId = null;

	public CentralManagementSystem(String fingerPrintFeatureSet, String faceRecognitionFeatureset, String rfidScan) {
		
		this.lock = new ReentrantLock();
		this.fingerPrintFeatureset = fingerPrintFeatureSet;
		this.faceRecognitionFeatureset = faceRecognitionFeatureset;
		this.rfidScan = rfidScan;

	}

	private Notification notification;

	public Boolean accessState = false;

	@Override
    public void run() {

        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("Admin Controls: ");
				System.out.println("Enter user management option(Type No to skip): ");
				String optionInput = scanner.nextLine();

				if(!optionInput.equals("No") && optionInput.equals("delete")) {
					System.out.println("Enter user id to delete");
					String userId = scanner.nextLine();
					this.userId = UUID.fromString(userId);
				}
				System.out.println("Enter config(Type No to skip)");
                String configInput = scanner.nextLine();

				if(!configInput.equals("No")) {
					String cleanInput = configInput.replaceAll("[{}\\s]", "");
					String[] booleanStrings = cleanInput.split(",");
					this.configInput = new ArrayList<>();
					for (String boolStr : booleanStrings) {
						this.configInput.add(Boolean.parseBoolean(boolStr.trim()));
					}
				}	

                lock.lock();
				this.centralProcessing();
			
            }
        } finally {
            scanner.close();
        }
    }

	private void centralProcessing() {
		AdminControlSystemOutput config = new AdminControlSystemOutput();
		config = adminControlSystem.adminControlProcessing(this.fingerPrintFeatureset, this.faceRecognitionFeatureset, this.rfidScan, this.accessState, this.userId, this.configInput, this.option);		
		this.accessState = config.accessState;
		
		if(this.option == null) {
			this.accessState = combinedVerificationSystem.combinedVerificationProcessing(this.fingerPrintFeatureset, this.faceRecognitionFeatureset, this.rfidScan, config.config);
		}
		
		System.out.println("accessState - " + config.accessState);
	}

}
