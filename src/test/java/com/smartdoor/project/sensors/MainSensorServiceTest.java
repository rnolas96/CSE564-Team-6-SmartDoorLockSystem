package com.smartdoor.project.sensors;

import com.smartdoor.models.Barcode;
import com.smartdoor.models.CameraParam;
import com.smartdoor.models.FeatureSet;
import com.smartdoor.models.Fingerprint;
import com.smartdoor.models.Photo;
import com.smartdoor.services.sensors.FaceRecogScanner;
import com.smartdoor.services.sensors.FingerprintScanner;
import com.smartdoor.services.sensors.MainSensorService;
import com.smartdoor.services.sensors.RFIDScanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class MainSensorServiceTest {

    private FingerprintScanner fingerprintScanner;
    private Fingerprint mockFingerprint;

    @BeforeEach
    void setUp() {
        fingerprintScanner = new FingerprintScanner();
        mockFingerprint = mock(Fingerprint.class);
    }

    @Test
    void biometricProcessor_ThrowsException() throws Exception {
        // Arrange
        boolean captured = true;
        String errorMessage = "fingerprint data not found";
        Fingerprint mockFingerprint = mock(Fingerprint.class);
        when(mockFingerprint.getValue()).thenReturn("sampleFingerprintValue");
        when(fingerprintScanner.getFeatureSet(mockFingerprint)).thenThrow(new Exception(errorMessage));
    
        // Act and Assert
        Exception exception = assertThrows(Exception.class, () -> {
            fingerprintScanner.biometricProcessor(captured, mockFingerprint, 1);
        });
    
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void run_RFIDScanner_ProcessesSuccessfully() throws Exception {
        // Arrange
        CountDownLatch latch = new CountDownLatch(1);
        String sensorName = "rfidScan";
        String sensorInput = "rfidInput";
        MainSensorService mainSensorService = new MainSensorService(sensorName, sensorInput, latch);

        FeatureSet valueToBeReturned = new FeatureSet();
        RFIDScanner mockRFIDScanner = mock(RFIDScanner.class);
        when(mockRFIDScanner.RFIDProcessor(eq(false), any(Barcode.class), eq(1))).thenReturn(valueToBeReturned);

        // Act
        mainSensorService.run();

        // Assert
        latch.await(); // Wait for the latch to count down
        verify(mockRFIDScanner).RFIDProcessor(eq(false), any(Barcode.class), eq(1));
    }

    @Test
    void run_FaceRecognitionScanner_ProcessesSuccessfully() throws Exception {
        // Arrange
        CountDownLatch latch = new CountDownLatch(1);
        String sensorName = "faceRecognition";
        String sensorInput = "faceRecognitionInput";
        MainSensorService mainSensorService = new MainSensorService(sensorName, sensorInput, latch);

        FeatureSet valueToBeReturned = new FeatureSet();
        FaceRecogScanner mockFaceRecogScanner = mock(FaceRecogScanner.class);
        when(mockFaceRecogScanner.faceRecogProcessor(eq(false), any(CameraParam.class), any(Photo.class), any(), eq(1))).thenReturn(valueToBeReturned);

        // Act
        mainSensorService.run();

        // Assert
        latch.await(); // Wait for the latch to count down
        verify(mockFaceRecogScanner).faceRecogProcessor(eq(false), any(CameraParam.class), any(Photo.class), any(), eq(1));
    }
}