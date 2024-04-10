package com.smartdoor.services.sensors;

import com.smartdoor.models.Barcode;
import com.smartdoor.models.FeatureSet;
import com.smartdoor.models.Notification;

public class RFID {

	private boolean captured;

	private FeatureSet scanned;

	private Barcode RFIDScan;

	private Notification Notification;

	private int c_id;

	private FeatureSet finalScan;

	private boolean checkScanned(boolean scanned) {
		return false;
	}

	private FeatureSet getFeatureSet(Barcode RFIDScan, FeatureSet scanned) {
		return null;
	}

	private Notification checkException(FeatureSet scanned, Notification notification) {
		return null;
	}

	public FeatureSet outFeatureSet(FeatureSet scanned, FeatureSet finalScan) {
		return null;
	}

	public void RFIDProcessor(boolean captured, FeatureSet scanned, Barcode RFIDScan, Notification notification, int c_id, FeatureSet finalScan) {

	}

}
