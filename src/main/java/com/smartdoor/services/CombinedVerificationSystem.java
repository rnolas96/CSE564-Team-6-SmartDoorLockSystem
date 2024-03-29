package com.smartdoor.services;

import java.util.List;

public class CombinedVerificationSystem {

	private Boolean isRFID;

	private Boolean isFingerPrint;

	private Boolean isFaceRecognition;

	public List<Boolean> config;

	public Boolean accessState = false;

	private void setIsRFIDGivenConfig(List<Boolean> config, Boolean isRFID) {

	}

	private void setIsFingerPrintGivenConfig(List<Boolean> config, Boolean isFingerPrint) {

	}

	private void setIsFaceRecognitionGivenConfig(List<Boolean> config, Boolean isFaceRecognition) {

	}

	public void combinedVerificationProcessing(String fingerPrintFeatureSet, String faceRecognitionFeatureSet, String rfidScan, List<Boolean> config, boolean accessState) {

	}

}
