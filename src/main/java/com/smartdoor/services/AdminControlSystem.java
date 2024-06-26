package com.smartdoor.services;

import java.util.List;
import java.util.UUID;

import com.smartdoor.models.AdminControlSystemOutput;
import com.smartdoor.models.Notification;
import com.smartdoor.models.User;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class AdminControlSystem {

	String keySerializer = StringSerializer.class.getName();
	String valueSerializer = StringSerializer.class.getName();
	NotificationService notificationService = new NotificationService("localhost:9092",StringSerializer.class.getName(),StringSerializer.class.getName());
	LoggingSystem logger = new LoggingSystem();
	private ArrayList<Boolean> configState = new ArrayList<>() {{
		add(true);
		add(true); 
		add(true);
	}};

	private boolean isRFID;

	private boolean isFingerPrint;

	private boolean isFaceRecognition;

	public DataService dataService = new DataService();

	private String option;

	private User user = new User();

	private UUID user_id;

	public boolean accessState = false;

	AdminControlSystemOutput acsOutput;

	private Notification notification = new Notification();

	private void setUserId(UUID user_id) {
		this.user_id = user_id;
	}

	private void setOption(String option) {
		this.option = option;
	}

	private void setConfig(ArrayList<Boolean> configState) {
		this.configState = configState;
	}

	private void setIsRFID(String rfidScan) {
		System.out.println("setting RFID");
		this.isRFID = dataService.match(rfidScan, "rfid_scan");
	}

	private void setIsFingerPrint(String fingerPrintFeatureSet) {
		System.out.println("setting fingerprint");
		this.isFingerPrint = dataService.match(fingerPrintFeatureSet, "fingerprint_scan");
	}

	private void setIsFaceRecognition(String faceRecognitionFeatureSet) {
		System.out.println("setting face recognition");
		this.isFaceRecognition = dataService.match(faceRecognitionFeatureSet, "face_recognition");
		
	}

	private Boolean setAccessStateAndHandleUserChanges(String option, Boolean acessState, UUID user_id, String faceRecognitionFeatureSet, String fingerPrintFeatureSet, String rfidScan) {
		
		System.out.println("option - " + option);
		System.out.println("user_id - " + user_id);
		if(this.user_id != null && this.option != null) {
			if (option.equals("update")) {
				this.user.updateUser(fingerPrintFeatureSet, faceRecognitionFeatureSet, rfidScan);
				System.out.println("user Updated successfully");
				logger.log_data("info", "user Updated successfully"+user_id);

				//notification user updated
				notification.message="user :"+ user_id + " user Updated successfully";
				notification.type = "alert";
				notification.topic = "notification";
				notificationService.notify(notification);

				return true;
			}
			else if(option.equals("add") && !this.isFaceRecognition && !this.isFingerPrint && !this.isRFID) {
				try {
					this.user.addUser(fingerPrintFeatureSet, faceRecognitionFeatureSet, rfidScan);

					// Notification user Added
					notification.message = "user :" + user_id + " user Added successfully";
					notification.type = "alert";
					notification.topic = "notification";
					notificationService.notify(notification);

					System.out.println("user Added successfully");
					logger.log_data("info","user Added successfully"+user_id);

					return true;
				}
				catch(Exception ex){
					System.out.println("failure : User was not Added");
					logger.log_error_data( "error","User was not Added");

				}
			}
			else if(option.equals("add") ) {
				// Notification user Added
				notification.message = "user :" + user_id + " user already exists";
				notification.type = "alert";
				notification.topic = "notification";
				notificationService.notify(notification);
				System.out.println("failure : User Already Exists");
				logger.log_error_data( "error","failure : User Already Exists");

			}
			else if(option.equals("delete")){
				System.out.println("user Deleted successfully");
				// notification delete
				notification.message="user :"+ user_id + " user Deleted successfully";
				notification.type = "alert";
				notification.topic = "notification";
				notificationService.notify(notification);

				user.deleteUser(user_id);

				logger.log_data("info",user_id+"user Deleted successfully");

			}
			else{
				System.out.println("invalid input");
			}
		}
		else {
			// notification
			notification.message="user Id missing: updation failerd";
			notification.type = "alert";

			notificationService.notify(notification);

			System.out.println("User_id missing, hence data updation does not take place");
			logger.log_error_data( "error","user Id missing: updation failerd");

		}
		return false;
	}

	public AdminControlSystemOutput adminControlProcessing(String fingerPrintFeatureSet, String faceRecognitionFeatureSet, String rfidScan, Boolean accessState, UUID user_id, ArrayList<Boolean> configInput, String option) {

		if(configInput != null && this.configState != configInput)
			this.setConfig(configInput);

		this.setUserId(user_id);
		this.setOption(option);
		this.setIsFaceRecognition(faceRecognitionFeatureSet);
		this.setIsFingerPrint(fingerPrintFeatureSet);
		this.setIsRFID(rfidScan);

		System.out.println(" isFaceRecognition - " + this.isFaceRecognition);
		System.out.println(" isFingerPrint - " + this.isFingerPrint);
		System.out.println(" isRFID - " + this.isRFID);

		this.accessState = this.setAccessStateAndHandleUserChanges(this.option, this.accessState, this.user_id, faceRecognitionFeatureSet, fingerPrintFeatureSet, rfidScan);

		AdminControlSystemOutput acsOutput = new AdminControlSystemOutput();

		acsOutput.accessState = this.accessState;
		acsOutput.config = this.configState;

		return acsOutput;
		
	}

}
