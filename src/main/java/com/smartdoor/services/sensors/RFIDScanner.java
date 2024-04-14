package com.smartdoor.services.sensors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartdoor.models.Barcode;
import com.smartdoor.models.FeatureSet;
import com.smartdoor.models.Notification;

import java.io.File;

public class RFIDScanner {

	private boolean captured;

	private FeatureSet scanned;

	private Barcode RFIDScan;

	private Notification Notification;

	private int c_id;

	private FeatureSet finalScan;

	String filePath = "CSE564-Team-6-SmartDoorLockSystem/src/main/java/com/smartdoor/data/RFIDFeatureMap.json";

	private boolean checkScanned(boolean scanned) {
		return false;
	}

	private FeatureSet getFeatureSet(Barcode RFIDScan) throws Exception {
		// Read the JSON file
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(new File(filePath));

		JsonNode rfid = rootNode.get(RFIDScan.value);

		if (rfid != null) {

			String rfidValue = rfid.get("value").asText();
			System.out.println("rfid="+rfidValue.toString());

			FeatureSet featureSet = new FeatureSet();
			featureSet.value = rfidValue;

			return featureSet;

		}
		else{

			throw new Exception("data not found");
		}

	}

	public FeatureSet RFIDProcessor(boolean captured, Barcode RFIDScan, int cf_id) throws Exception {
		while(!captured){
			try {
				scanned = getFeatureSet(RFIDScan);
				captured = true;
			}
			catch (Exception ex){
				throw new Exception("error occurred while getting featureset" +  ex.getMessage());

			}
		}

		finalScan = scanned;
		return finalScan;
	}

}
