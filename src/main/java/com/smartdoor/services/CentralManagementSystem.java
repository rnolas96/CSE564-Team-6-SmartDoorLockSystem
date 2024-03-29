package com.smartdoor.services;

import java.util.List;

import com.smartdoor.models.Notification;

public class CentralManagementSystem {

	private AdminControlSystem adminControlSystem;

	private CombinedVerificationSystem combinedVerificationSystem;

	private String fingerPrintFeatureset;

	private String faceRecognitionFeatureset;

	private String rfidScan;

	private int configInput;

	private String option;

	private int userId;

	private Notification notification;

	public Boolean accessState = false;

	public void centralProcessing(String fingerPrintFeatureSet, String faceRecognitionFeatureSet, String rfidScan, List<Boolean> config_input, String option, int userId, Notification notification, boolean acessState) {

	}

}
