package com.smartdoor.services.sensors;

import com.smartdoor.exceptions.UnauthorizedException;
import com.smartdoor.models.FeatureSet;
import com.smartdoor.models.Fingerprint;
import com.smartdoor.models.Notification;

import java.util.HashMap;
import java.util.Map;

public class FingerprintScanner {

	private boolean captured;

	private FeatureSet scanned;

	private Notification notification;

	private Fingerprint Scan;

	private int c_id;

	private FeatureSet finalScan;

	Map<String, String> featureSetMap = new HashMap<>();

	private boolean checkCaptured(boolean captured) {
		if(captured){
			return true;
		}
		else {
			return false;
		}
	}

	private FeatureSet getFeatureSet(Fingerprint scan) {
		String Scanned = featureSetMap.get(scan.name);
		return scanned;
	}

	public FeatureSet outFeatureSet(FeatureSet scanned, FeatureSet finalScan) {
		return null;
	}

	public FeatureSet biometricProcessor(boolean captured, FeatureSet scanned, Notification notification, Fingerprint scan, int c_id) {
		while(!captured){
			try {
				scanned = getFeatureSet(scan);
				captured = true;
			}
			catch (UnauthorizedException ex){
				if(ex.getStatusCode()==401){
					//send Notification
				}

			}
		}

		finalScan = scanned;
		return finalScan;

	}

}
