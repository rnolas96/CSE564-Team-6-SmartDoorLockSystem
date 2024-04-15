package com.smartdoor.services;

import java.util.ArrayList;
import java.util.List;

import com.smartdoor.models.AdminControlSystemOutput;
import com.smartdoor.models.Notification;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;

public class CombinedVerificationSystem {

	String keySerializer = StringSerializer.class.getName();
	String valueSerializer = StringSerializer.class.getName();
	NotificationService notificationService = new NotificationService("localhost:9092",StringSerializer.class.getName(),StringSerializer.class.getName());
	private Boolean isRFID;
	private Boolean isFingerPrint;

	private Boolean isFaceRecognition;

	public DataService dataService = new DataService();
	DoorLockService doorLockService = new DoorLockService();

	public List<Boolean> config;

	private Notification notification = new Notification();

	private  String message;

	private String notificationTopic = "notification";

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
			System.out.println("Authorization successful: Access Granted");
			notification.message = "Authorization successful: Access Granted ";
			notification.type = "alert";
			notification.topic = notificationTopic;

			notificationService.notify(notification);
			accessState = true;
			doorLockService.setLockedState(accessState);
		}
		else{
			notification.message = "Authorization unSuccessful: Access Denied ";
			System.out.println("Authorization unSuccessful: Access Denied");
			notification.type = "alert";
			notification.topic = notificationTopic;
			doorLockService.setLockedState(accessState);


			notificationService.notify(notification);
		}

		if(this.isFaceRecognition == null) {

			//send notification
			notification.message = "facerecognition authorization failed";
			notification.type = "alert";
			notification.topic = notificationTopic;
			notificationService.notify(notification);

		}
		if (this.isFingerPrint == null) {

			//send notification
			notification.message = "fingerprint authorization failed";
			notification.type = "alert";
			notification.topic = notificationTopic;


			notificationService.notify(notification);

		}
		if (this.isRFID == null) {

			//send notification
			notification.message = "rfid authorization failed";
			notification.type = "alert";
			notification.topic = notificationTopic;

			notificationService.notify(notification);

		}

		return accessState;
	}

}
