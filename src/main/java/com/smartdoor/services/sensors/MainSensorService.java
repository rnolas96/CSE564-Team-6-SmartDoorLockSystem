package com.smartdoor.services.sensors;

import java.util.concurrent.CountDownLatch;


import com.smartdoor.models.*;


public class MainSensorService implements Runnable{
    private final String sensorName;
    private final String sensorInput;
    private final CountDownLatch latch;

    private static FeatureSet fpOutput;
    private static FeatureSet rfidOutput;
    private static FeatureSet camOutput;


    public MainSensorService(String sensorName, String sensorInput, CountDownLatch latch) {
        this.sensorName = sensorName;
        this.sensorInput = sensorInput;
        this.latch = latch;
    }
    public static FeatureSet getFingerPrintOutput() {
        return fpOutput;
    }
    public static FeatureSet getRFIDOutput() {
        return rfidOutput;
    }

    public static  FeatureSet getCamOutput(){
        return camOutput;
    }
    @Override
    public void run() {
        // Simulate processing for the sensor
        System.out.println("Processing for " + sensorName + " started with input: " + sensorInput);


        switch (sensorName) {
            case "fingerprint" -> {
                try {
                    FingerprintScanner fp = new FingerprintScanner();
                    Fingerprint fingerprint = new Fingerprint();
                    fingerprint.value = this.sensorInput;
                    fpOutput = fp.biometricProcessor(false, fingerprint, 1);
                    System.out.println("output - " + this.fpOutput.value);
                    Thread.sleep(2000);
                    System.out.println("Waiting for sleep");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case "rfidScan" -> {
                try {
                    RFIDScanner rfid = new RFIDScanner();
                    Barcode rfidScan = new Barcode();
                    rfidScan.value = this.sensorInput;
                    rfidOutput= rfid.RFIDProcessor(false, rfidScan, 1);
                    Thread.sleep(5000);
                    System.out.println("Waiting for sleep");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case "faceRecognition" -> {
                try {
                    FaceRecogScanner faceRecognition = new FaceRecogScanner();
                    Photo photo = new Photo();
                    Feedback feedback = new Feedback();
                    CameraParam cameraParam = new CameraParam();
                    photo.value = this.sensorInput;
                    camOutput= faceRecognition.faceRecogProcessor(false, cameraParam, photo, feedback, 1);
                    Thread.sleep(5000);
                    System.out.println("Waiting for sleep");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Processing for " + sensorName + " completed.");
        // Count down the latch to indicate that this sensor thread has finished processing
        latch.countDown();
    }
}
