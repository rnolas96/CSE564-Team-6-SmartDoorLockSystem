package com.smartdoor.services.sensors;

import com.smartdoor.models.FeatureSet;
import com.smartdoor.models.Feedback;
import com.smartdoor.models.Notification;
import com.smartdoor.models.Photo;

public class FaceRecognition {

	private boolean captured;

	private int cameraParam;

	private FeatureSet scanned;

	private int dist_threshold;

	private Photo photo;

	private int updatedCameraParam;

	private Feedback feedback;

	private boolean checkCaptured(boolean captured) {
		return false;
	}

	private boolean checkDistance(int dist_threshold, Photo photo) {
		return false;
	}

	private FeatureSet getFeatureSet(Photo photo, FeatureSet scanned) {
		return null;
	}

	private Feedback checkException(FeatureSet scanned, Feedback feedback) {
		return null;
	}

	public FeatureSet outFeatureSet(FeatureSet scanned, FeatureSet finalScan) {
		return null;
	}

	public void faceRecogProcessor(boolean captured, FeatureSet scanned, Notification notification, Photo photo, int c_id, FeatureSet FinalScan) {

	}

}
