package com.smartdoor.project.sensors;

import com.smartdoor.models.*;
import com.smartdoor.services.sensors.FaceRecogScanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;


import static org.junit.jupiter.api.Assertions.*;

public class FaceRecogScannerTest {

    private FaceRecogScanner faceRecogScanner;

    @BeforeEach
    void setUp() {
        faceRecogScanner = new FaceRecogScanner();
    }

    @Test
    void checkDistance_DistanceLessThanThreshold_UpdateCameraParam() {
        // Arrange
        int distThreshold = 50;
        Photo photo = new Photo();
        photo.distance = 40;
        CameraParam cameraParam = new CameraParam();
        cameraParam.lightIntensity = 50;
        cameraParam.cameraAngle = 30;

        // Act
        CameraParam updatedCameraParam = faceRecogScanner.checkDistance(distThreshold, photo, cameraParam);

        // Assert
        assertEquals(photo.lightIntensity, updatedCameraParam.lightIntensity);
        assertEquals(photo.cameraAngle, updatedCameraParam.cameraAngle);
    }

    @Test
    void checkDistance_DistanceGreaterThanThreshold_NoUpdate() {
        // Arrange
        int distThreshold = 50;
        Photo photo = new Photo();
        photo.distance = 60;
        CameraParam cameraParam = new CameraParam();
        cameraParam.lightIntensity = 50;
        cameraParam.cameraAngle = 30;

        // Act
        CameraParam updatedCameraParam = faceRecogScanner.checkDistance(distThreshold, photo, cameraParam);

        // Assert
        assertEquals(cameraParam.lightIntensity, updatedCameraParam.lightIntensity);
        assertEquals(cameraParam.cameraAngle, updatedCameraParam.cameraAngle);
    }

    @Test
    void getFeatureSet_ValidPhoto_FeatureSetReturned() throws Exception {
        // Arrange
        String filePath = "src/main/java/com/smartdoor/data/PhotoFeatureMap.json";
        FileWriter file = new FileWriter(filePath);
        file.write("{\"user3Photo\": {\"value\": \"1100\", \"lightIntensity\": 70, \"cameraAngle\": 35}}");
        file.close();

        Photo photo = new Photo();
        photo.value = "user3Photo";

        // Act
        FeatureSet featureSet = faceRecogScanner.getFeatureSet(photo);

        // Assert
        assertNotNull(featureSet);
        assertEquals("1100", featureSet.value);

        // Clean up
        File testFile = new File(filePath);
        testFile.delete();
    }

    @Test
    void getFeatureSet_InvalidPhoto_ExceptionThrown() {
        // Arrange
        Photo photo = new Photo();
        photo.value = "invalidPhoto";

        // Act & Assert
        assertThrows(Exception.class, () -> {
            faceRecogScanner.getFeatureSet(photo);
        });
    }

    // testing the feedback for light intensity adjustment
    @Test
    void getFeedback_LightIntensityLessThanThreshold_IncrementLightIntensity() {
        this is to test the feedback case .
        // Arrange
        CameraParam updatedCameraParam = new CameraParam();
        updatedCameraParam.lightIntensity = 70;
        Photo photo = new Photo();
        photo.distance = 40;
        Feedback feedback = new Feedback();

        // Act
        boolean validPhoto = faceRecogScanner.getFeedback(updatedCameraParam, photo, feedback, false);

        // Assert
        assertFalse(validPhoto);
        assertEquals(80, updatedCameraParam.lightIntensity);
    }

    @Test
    void getFeedback_CameraAngleLessThanThreshold_IncrementCameraAngle() {
        // Arrange
        CameraParam updatedCameraParam = new CameraParam();
        updatedCameraParam.cameraAngle = 40;
        Photo photo = new Photo();
        photo.distance = 40;
        Feedback feedback = new Feedback();

        // Act
        boolean validPhoto = faceRecogScanner.getFeedback(updatedCameraParam, photo, feedback, false);

        // Assert
        assertFalse(validPhoto);
        assertEquals(50, updatedCameraParam.cameraAngle);
    }

    @Test
    void getFeedback_DistanceGreaterThanThreshold_DecrementDistance() {
        // Arrange
        CameraParam updatedCameraParam = new CameraParam();
        updatedCameraParam.cameraAngle = 40;
        Photo photo = new Photo();
        photo.distance = 60;
        Feedback feedback = new Feedback();

        // Act
        boolean validPhoto = faceRecogScanner.getFeedback(updatedCameraParam, photo, feedback, false);

        // Assert
        assertFalse(validPhoto);
        assertEquals(50, photo.distance);
    }


    @Test
    void faceRecogProcessor_NotCaptured_ThrowsException() {
        // Arrange
        boolean captured = false;
        CameraParam cameraParam = new CameraParam();
        Photo photo = new Photo();
        Feedback feedback = new Feedback();
        int c_id = 123;

        // Act & Assert
        assertThrows(Exception.class, () -> {
            faceRecogScanner.faceRecogProcessor(captured, cameraParam, photo, feedback, c_id);
        });
    }
}
