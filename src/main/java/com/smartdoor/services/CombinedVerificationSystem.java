package com.smartdoor.services;

import java.util.ArrayList;
import java.util.List;

import com.smartdoor.models.AdminControlSystemOutput;
import com.smartdoor.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;

public class CombinedVerificationSystem {

	NotificationService notificationService = new NotificationService();
	private Boolean isRFID;
	private Boolean isFingerPrint;

	private Boolean isFaceRecognition;

	public DataService dataService = new DataService();

	public List<Boolean> config;

	private Notification notification = new Notification();

	private  String message;

	private void setConfig(ArrayList<Boolean> config) {
		this.config = config;
	}

	private void setIsRFID(String rfidScan) {
		if(!this.config.get(0))
			this.isRFID = true;
		else
			this.isRFID = dataService.match(rfidScan, "rfid_scan");
	}

	private void setIsFingerPrint(String fingerPrintFeatureSet) {
		if(!this.config.get(1))
			this.isFingerPrint = true;
		else
			this.isFingerPrint = dataService.match(fingerPrintFeatureSet, "fingerprint_scan");
	}

	private void setIsFaceRecognition(String faceRecognitionFeatureSet) {
		if(!this.config.get(2))
			this.isFaceRecognition = true;
		else
			this.isFaceRecognition = dataService.match(faceRecognitionFeatureSet, "face_recognition");
		
	}

	public Boolean combinedVerificationProcessing(String fingerPrintFeatureSet, String faceRecognitionFeatureSet, String rfidScan, ArrayList<Boolean> config) {
		
		Boolean accessState = false;
		
		this.setConfig(config);

		this.setIsFaceRecognition(faceRecognitionFeatureSet);
		this.setIsFingerPrint(fingerPrintFeatureSet);
		this.setIsRFID(rfidScan);

		if(this.isFaceRecognition && this.isFingerPrint && this.isRFID) {
			//notify authentication success
			notification.message = "Authorization successful: Access Granted ";
			notification.type = "alert";

			notificationService.notify(notification);
			accessState = true;
		}
		else{
			notification.message = "Authorization unSuccessful: Access Denied ";
			notification.type = "alert";

			notificationService.notify(notification);
		}

		if(this.isFaceRecognition == null) {

			//send notification
			notification.message = "facerecognition authorization failed";
			notification.type = "alert";
			notificationService.notify(notification);

		}
		if (this.isFingerPrint == null) {

			//send notification
			notification.message = "fingerprint authorization failed";
			notification.type = "alert";

			notificationService.notify(notification);

		}
		if (this.isRFID == null) {

			//send notification
			notification.message = "rfid authorization failed";
			notification.type = "alert";

			notificationService.notify(notification);

		}

		return accessState;
	}

}
