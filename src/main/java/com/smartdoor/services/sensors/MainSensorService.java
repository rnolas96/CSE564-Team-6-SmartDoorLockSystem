package com.smartdoor.services.sensors;

import java.util.concurrent.CountDownLatch;

import com.smartdoor.models.Barcode;
import com.smartdoor.models.FeatureSet;
import com.smartdoor.models.Fingerprint;

public class MainSensorService implements Runnable{
    private final String sensorName;
    private final String sensorInput;
    private final CountDownLatch latch;

    private FeatureSet output;

    public MainSensorService(String sensorName, String sensorInput, CountDownLatch latch, FeatureSet output) {
        this.sensorName = sensorName;
        this.sensorInput = sensorInput;
        this.latch = latch;
        this.output = output;
    }

    @Override
    public void run() {
        // Simulate processing for the sensor
        System.out.println("Processing for " + sensorName + " started with input: " + sensorInput);

        if(sensorName.equals("fingerprint")) {
            try {
                FingerprintScanner fp = new FingerprintScanner();
                Fingerprint fingerprint = new Fingerprint();
                fingerprint.value = this.sensorInput;
                this.output = fp.biometricProcessor(false, fingerprint, 1);
                System.out.println("output - " + this.output.value);
                Thread.sleep(2000);
                System.out.println("Waiting for sleep");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(sensorName.equals("rfidScan")) {
            try {
                RFIDScanner rfid = new RFIDScanner();
                Barcode rfidScan = new Barcode();
                rfidScan.value = this.sensorInput;
                System.out.println("response "+ rfid.RFIDProcessor(false, rfidScan, 1)); 
                Thread.sleep(5000);
                System.out.println("Waiting for sleep");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Processing for " + sensorName + " completed.");
        // Count down the latch to indicate that this sensor thread has finished processing
        latch.countDown();
    }
}
