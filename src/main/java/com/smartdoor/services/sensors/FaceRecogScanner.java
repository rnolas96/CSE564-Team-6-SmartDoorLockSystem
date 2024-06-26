package com.smartdoor.services.sensors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartdoor.models.*;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;

public class FaceRecogScanner {

	private boolean captured;

	private int cameraParam;

	private FeatureSet scanned;

	private FeatureSet finalScan;

	private int dist_threshold;

	private Photo photo;

	private CameraParam updatedCameraParam;

	private Feedback feedback;

	String filePath = "src/main/java/com/smartdoor/data/PhotoFeatureMap.json";

	public CameraParam checkDistance(int dist_threshold, Photo photo, CameraParam cameraParam) {
		if (photo.distance < dist_threshold) {
			cameraParam.lightIntensity = photo.lightIntensity;
			cameraParam.cameraAngle = photo.cameraAngle;
		};

		return cameraParam;
    }

	public FeatureSet getFeatureSet(Photo photo) throws Exception {

		// Read the JSON file
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(new File(filePath));

		JsonNode capturedPhoto = rootNode.get(photo.value);

		if (capturedPhoto != null) {
			String photoValue = capturedPhoto.get("value").asText();
			System.out.println("photo "+ photoValue);

			FeatureSet featureSet = new FeatureSet();
			featureSet.value = photoValue;

			return featureSet;
		}
		else {
			throw new Exception("photo data not found");
		}
    }

	public boolean getFeedback(CameraParam updatedCameraParam, Photo photo, Feedback feedback, boolean validPhoto) {
		System.out.println("Correcting camera angle and light intensity.");
		if (updatedCameraParam.lightIntensity < 80){
			updatedCameraParam.lightIntensity += 10;
			System.out.println("corrected lightIntensity"+updatedCameraParam.lightIntensity);

		}
		if (updatedCameraParam.cameraAngle < 45) {
			updatedCameraParam.cameraAngle += 5;
			System.out.println("corrected cameraAngle"+updatedCameraParam.cameraAngle);

		}
		if (photo.distance > dist_threshold) {
			photo.distance -= 10;
		}
		validPhoto = updatedCameraParam.lightIntensity == 80 & updatedCameraParam.cameraAngle == 45 ;

        feedback.updatedCameraParam = updatedCameraParam;
		feedback.photo = photo;
		feedback.validPhoto = validPhoto;
		return validPhoto;
	}

	public FeatureSet faceRecogProcessor(boolean captured, CameraParam cameraParam, Photo photo, Feedback feedback, int c_id )throws Exception {
		int counter = 0;
		boolean validPhoto = false;

		while(!captured){
			try{
				if (counter > 0) {
					photo = feedback.photo;
					cameraParam = feedback.updatedCameraParam;
				}
				counter += 1;
				updatedCameraParam = checkDistance(dist_threshold, photo, cameraParam);
				scanned = getFeatureSet(photo);
				captured = true;
			}
			catch (Exception ex){

				//validPhoto = getFeedback(updatedCameraParam, photo, feedback, validPhoto);

				while(!validPhoto) {
					validPhoto = getFeedback(updatedCameraParam, photo, feedback, validPhoto);
				}

				if (validPhoto) {
					System.out.println("vallid photo value ======"+validPhoto);
					JSONObject jsonObject = new JSONObject();

					jsonObject.put("lightIntensity",  feedback.updatedCameraParam.lightIntensity);
					jsonObject.put("cameraAngle", feedback.updatedCameraParam.cameraAngle);
					System.out.println("featureset generated from face recognition scanner"+jsonObject.get("value"));

					FeatureSet featureSet = new FeatureSet();

					featureSet.value =  photo.value;

					return featureSet;


				}

				System.out.println("adjusting camera angle and ligght intensity based on feedback" +  ex.getMessage());

            }
		}

		finalScan = scanned != null ? scanned : new FeatureSet(); // Return an empty FeatureSet if scanned is null
		return finalScan;
	}

}
