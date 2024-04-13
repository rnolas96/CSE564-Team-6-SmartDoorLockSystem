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

	private ArrayList<Boolean> config = new ArrayList<Boolean>() {{
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
				
				if(!optionInput.equals("No")) {
					this.option = optionInput;

					if(optionInput.equals("delete") || optionInput.equals("update")) {
						System.out.println("Enter user id to update/delete");
						String userId = scanner.nextLine();
						try {
							this.userId = UUID.fromString(userId);
						} catch (Exception e) {
							System.out.println("Invalid UUID entered");
						}
					}
					else {
						this.userId = UUID.randomUUID();
					}
				}
				System.out.println("Enter config(Type No to skip)");
                String configInput = scanner.nextLine();

				if(!configInput.equals("No")) {
					String cleanInput = configInput.replaceAll("[{}\\s]", "");
					String[] booleanStrings = cleanInput.split(",");
					this.config = new ArrayList<>();
					for (String boolStr : booleanStrings) {
						this.config.add(Boolean.parseBoolean(boolStr.trim()));
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

		if(this.option != null) {
			System.out.println("comes here to admin case");
			AdminControlSystemOutput acsOutput = new AdminControlSystemOutput();
			acsOutput = adminControlSystem.adminControlProcessing(this.fingerPrintFeatureset, this.faceRecognitionFeatureset, this.rfidScan, this.accessState, this.userId, this.config, this.option);		
			this.accessState = acsOutput.accessState;
			System.out.println("accessState - " + acsOutput.accessState);
		}
		else {
			System.out.println("comes here to cvs case");
			this.accessState = combinedVerificationSystem.combinedVerificationProcessing(this.fingerPrintFeatureset, this.faceRecognitionFeatureset, this.rfidScan, this.config);
		}
		
	}

}
