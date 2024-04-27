package com.smartdoor.services;

import java.util.HashMap;
import java.util.Map;

public class DataService {
    private Map<String, Map<String, String>> mockData;

    public DataService() {
        this.mockData = new HashMap<>();

        Map<String, String> faceRecognitionData = new HashMap<>();
        faceRecognitionData.put("550e8400-e29b-41d4-a716-446655440000", "010001");
        faceRecognitionData.put("3d813cbb-47fb-32ba-91df-831e1593ac29", "1100");
        this.mockData.put("face_recognition", faceRecognitionData);

        Map<String, String> rfidScanData = new HashMap<>();
        rfidScanData.put("550e8400-e29b-41d4-a716-446655440000", "1000101");
        rfidScanData.put("3d813cbb-47fb-32ba-91df-831e1593ac29", "110011");
        this.mockData.put("rfid_scan", rfidScanData);

        Map<String, String> fingerprintScanData = new HashMap<>();
        fingerprintScanData.put("550e8400-e29b-41d4-a716-446655440000", "000101");
        fingerprintScanData.put("3d813cbb-47fb-32ba-91df-831e1593ac29", "110010");
        this.mockData.put("fingerprint_scan", fingerprintScanData);
    }

    public boolean match(String input, String scanType) {

        System.out.println("input - " + input);

        Map<String, String> scanData = this.mockData.get(scanType);
        if (scanData == null) {
            System.out.println("scanData is empty");
            return false;
        }
        for (String userId : scanData.keySet()) {
            System.out.println("userId in match for data service - " + userId);
            System.out.println("value - " + scanData.get(userId).equals(input));
            if (scanData.get(userId).equals(input)) {
                System.out.println("It's a match!!");
                return true;
            }
        }
        return false; 
    }

}