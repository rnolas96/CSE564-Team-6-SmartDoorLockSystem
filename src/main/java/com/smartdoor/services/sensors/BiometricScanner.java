package com.smartdoor.services.sensors;

import com.smartdoor.models.FeatureSet;
import com.smartdoor.models.Fingerprint;
import com.smartdoor.models.Notification;

public class BiometricScanner {

	private boolean captured;

	private FeatureSet scanned;

	private Notification notification;

	private Fingerprint Scan;

	private int c_id;

	private FeatureSet finalScan;

	private boolean checkCaptured(boolean captured) {
		return false;
	}

	private FeatureSet getFeatureSet(Fingerprint Scan, FeatureSet scanned) {
		return null;
	}

	private Notification checkException(FeatureSet scanned, Notification notification) {
		return null;
	}

	public FeatureSet outFeatureSet(FeatureSet scanned, FeatureSet finalScan) {
		return null;
	}

	public void biometricProcessor(boolean captured, FeatureSet scanned, Notification notification, FeatureSet Scan, int c_id, FeatureSet FinalScan) {

	}

}
