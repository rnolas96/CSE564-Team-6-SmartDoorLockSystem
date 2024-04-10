package com.smartdoor.services;

import java.util.HashMap;
import java.util.Map;

public class DataService {
    private Map<String, Map<String, String>> mockData;

    public DataService() {
        mockData = new HashMap<>();

        Map<String, String> faceRecognitionData = new HashMap<>();
        faceRecognitionData.put("u_id1", "1000101");
        faceRecognitionData.put("u_id2", "1001010");
        mockData.put("face_recognition", faceRecognitionData);

        Map<String, String> rfidScanData = new HashMap<>();
        rfidScanData.put("u_id1", "1000101");
        rfidScanData.put("u_id2", "1001010");
        mockData.put("rfid_scan", rfidScanData);

        Map<String, String> fingerprintScanData = new HashMap<>();
        fingerprintScanData.put("u_id1", "1000101");
        fingerprintScanData.put("u_id2", "1001010");
        mockData.put("fingerprint_scan", fingerprintScanData);
    }

    public boolean match(String scanType, String input) {
        Map<String, String> scanData = mockData.get(scanType);
        if (scanData == null) {
            return false;
        }
        
        for (String userId : scanData.keySet()) {
            if (scanData.get(userId).equals(input)) {
                return true;
            }
        }
        return false; 
    }

}