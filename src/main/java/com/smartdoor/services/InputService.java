package com.smartdoor.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import com.smartdoor.models.FeatureSet;
import com.smartdoor.services.sensors.MainSensorService;

public class InputService  {
    private final CountDownLatch latch = new CountDownLatch(3);

    private FeatureSet fingerPrintFeatureSet = new FeatureSet();
    private FeatureSet faceRecognitionFeatureSet = new FeatureSet();
    private FeatureSet rfidFeatureSet = new FeatureSet();


    private void callCentralManagementSystem(FeatureSet fingerPrintFeatureSet, FeatureSet faceRecognitionFeatureset, FeatureSet rfidScan) {

        CentralManagementSystem centralManagementSystem = new CentralManagementSystem(fingerPrintFeatureSet, faceRecognitionFeatureset, rfidScan);
        // Implement code to call Central Management System
        centralManagementSystem.start();
        System.out.println("Central Management System called.");
    }
    private String getSensorInput(String sensorName) {

        Scanner scanner = new Scanner(System.in);
        String input = "";

        try {
            if(sensorName == "fingerprint") {
                System.out.println("Enter the fingerprint -");
                input = scanner.nextLine();  //"fingerPrintUser1";
            }
            else if(sensorName == "rfidScan") {
                System.out.println("Place the rfid -");
                input = scanner.nextLine(); // "RFIDUser1";
            }
            else {
                System.out.println("Face the camera -");
                input = scanner.nextLine();  //"faceRecognitionUser1";
            }
            return input;

        } finally {
        }

    }

    public void startSensorThreads() {
        // Start a thread for each sensor with its corresponding input
        Thread sensor1Thread = new Thread(new MainSensorService("fingerprint", getSensorInput("fingerprint"), latch));
        Thread sensor2Thread = new Thread(new MainSensorService("faceRecognition", getSensorInput("faceRecognition"), latch));
        Thread sensor3Thread = new Thread(new MainSensorService("rfidScan", getSensorInput("rfidScan"), latch));

        sensor1Thread.start();
        sensor2Thread.start();
        sensor3Thread.start();

        try {
            // Wait for all sensor threads to finish
            latch.await();
            System.out.println("All sensor threads have finished processing.");

            this.fingerPrintFeatureSet = MainSensorService.getFingerPrintOutput();
            this.faceRecognitionFeatureSet = MainSensorService.getCamOutput();
            this.rfidFeatureSet = MainSensorService.getRFIDOutput();



            // Call middleware service after all sensor threads have finished
            this.callCentralManagementSystem(this.fingerPrintFeatureSet, this.faceRecognitionFeatureSet, this.rfidFeatureSet);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    
}
