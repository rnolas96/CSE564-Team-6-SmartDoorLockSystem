package com.smartdoor.services.sensors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartdoor.exceptions.UnauthorizedException;
import com.smartdoor.models.FeatureSet;
import com.smartdoor.models.Fingerprint;
import com.smartdoor.models.Notification;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FingerprintScanner {

	private boolean captured;

	private FeatureSet scanned;

	private Notification notification;

	private Fingerprint Scan;

	private int c_id;

	private FeatureSet finalScan;

	String filePath = "src/main/java/com/smartdoor/data/FingerPrintFeatureSetMap.json";
	// String filePath = "./src/main/java/com/smartdoor/data/FingerPrintFeatureSetMap.json";

	private boolean checkCaptured(boolean captured) {
        return captured;
	}

	private FeatureSet getFeatureSet(Fingerprint scan) throws Exception {


		// Read the JSON file
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(new File(filePath));

		JsonNode fingerprint = rootNode.get(scan.value);

		if (fingerprint != null) {
			String fingerprintValue = fingerprint.get("value").asText();
			System.out.println("fingerPrint"+ fingerprintValue);

			FeatureSet featureSet = new FeatureSet();
			featureSet.value = fingerprintValue;

			return featureSet;
		}
		else{

			throw new Exception("fingerprint data not found");
		}

	}

	public FeatureSet biometricProcessor(boolean captured, Fingerprint scan, int c_id) throws Exception {
		while(!captured){
			try {
				scanned = getFeatureSet(scan);
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
