package com.smartdoor.services;

import java.util.List;
import java.util.ArrayList;

public class AdminControlSystem {

	private List<Boolean> configState = new ArrayList<>() {{
		add(true);
		add(true); 
		add(true);
	}};

	private boolean isRFID;

	private boolean isFingerPrint;

	private boolean isFaceRecognition;

	public boolean accessState = false;

	private void setConfig(List<Boolean> configState) {

	}

	private void setIsRFID(String rfidScan, Boolean isRFID) {

	}

	private void setIsFingerPrint(String fingerPrintFeatureSet, Boolean isFingerPrint) {

	}

	private void setIsFaceRecognition(String faceRecognitionFeatureSeet, Boolean isFaceRecognition) {

	}

	private void setAccessStateAndHandleUserChanges(String option, Boolean acessState) {

	}

	public void adminControlProcessing(String fingerPrintFeatureSet, String faceRecognitionFeatureSet, String rfidScan, Boolean accessState) {

	}

}
