package com.smartdoor.services;

import java.util.List;
import java.util.UUID;

import com.smartdoor.models.AdminControlSystemOutput;
import com.smartdoor.models.Notification;
import com.smartdoor.models.User;

import java.util.ArrayList;

public class AdminControlSystem {

	private ArrayList<Boolean> configState = new ArrayList<>() {{
		add(true);
		add(true); 
		add(true);
	}};

	private boolean isRFID;

	private boolean isFingerPrint;

	private boolean isFaceRecognition;

	private DataService dataService = new DataService();

	private String option;

	private User user;

	private UUID user_id;

	public boolean accessState = false;

	AdminControlSystemOutput acsOutput;

	private Notification notification;

	public void setConfig(ArrayList<Boolean> configState) {
		this.configState = configState;
	}

	private void setIsRFID(String rfidScan) {
		this.isRFID = dataService.match(rfidScan, "rfid_scan");
	}

	private void setIsFingerPrint(String fingerPrintFeatureSet) {
		this.isFingerPrint = dataService.match(fingerPrintFeatureSet, "fingerprint_scan");
	}

	private void setIsFaceRecognition(String faceRecognitionFeatureSet) {
		this.isFaceRecognition = dataService.match(faceRecognitionFeatureSet, "face_recognition");
		
	}

	private Boolean setAccessStateAndHandleUserChanges(String option, Boolean acessState, UUID user_id, String faceRecognitionFeatureSet, String fingerPrintFeatureSet, String rfidScan) {
		if(user_id != null && option != null) {
			if (option.equals("update")) {
				user.updateUser(fingerPrintFeatureSet, faceRecognitionFeatureSet, rfidScan);
				return true;
			}
			else if(option.equals("add")) {
				user.addUser(fingerPrintFeatureSet, faceRecognitionFeatureSet, rfidScan);
				return true;
			}
			else {
				user.deleteUser(user_id);
			}
		}
		return false;
	}

	public AdminControlSystemOutput adminControlProcessing(String fingerPrintFeatureSet, String faceRecognitionFeatureSet, String rfidScan, Boolean accessState, UUID user_id, ArrayList<Boolean> configInput, String option) {

		if(configInput != null && this.configState != configInput)
			this.setConfig(configInput);

		this.setIsFaceRecognition(faceRecognitionFeatureSet);
		this.setIsFingerPrint(fingerPrintFeatureSet);
		this.setIsRFID(rfidScan);

		if(this.isRFID && this.isFaceRecognition && this.isFingerPrint) {
			this.accessState = this.setAccessStateAndHandleUserChanges(this.option, this.accessState, this.user_id, faceRecognitionFeatureSet, fingerPrintFeatureSet, rfidScan);
		}

		AdminControlSystemOutput acsOutput = new AdminControlSystemOutput();

		acsOutput.accessState = this.accessState;
		acsOutput.config = this.configState;

		return acsOutput;
		
	}

}
