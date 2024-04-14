package com.smartdoor.project;

import com.smartdoor.services.CombinedVerificationSystem;
import com.smartdoor.services.DataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CombinedVerificationSystemTest {

    private CombinedVerificationSystem combinedVerificationSystem;

    @Mock
    private DataService mockDataService;

    @BeforeEach
    void setUp() {
        mockDataService = mock(DataService.class);
        combinedVerificationSystem = new CombinedVerificationSystem();
        combinedVerificationSystem.dataService = mockDataService;
    }

    @Test
    void combinedVerificationProcessing_AllTrue() {
        // Arrange
        String fingerPrintFeatureSet = "validFingerPrintFeatureSet";
        String faceRecognitionFeatureSet = "validFaceRecognitionFeatureSet";
        String rfidScan = "validRfidScan";
        ArrayList<Boolean> config = new ArrayList<>(List.of(true, true, true));
        when(mockDataService.match(any(), any())).thenReturn(true);

        // Act
        boolean result = combinedVerificationSystem.combinedVerificationProcessing(
                fingerPrintFeatureSet, faceRecognitionFeatureSet, rfidScan, config);

        // Assert
        assertTrue(result);
    }

    @Test
    void combinedVerificationProcessing_AllFalse() {
        // Arrange
        String fingerPrintFeatureSet = "invalidFingerPrintFeatureSet";
        String faceRecognitionFeatureSet = "invalidFaceRecognitionFeatureSet";
        String rfidScan = "invalidRfidScan";
        ArrayList<Boolean> config = new ArrayList<>(List.of(true, true, true));
        when(mockDataService.match(any(), any())).thenReturn(false);

        // Act
        boolean result = combinedVerificationSystem.combinedVerificationProcessing(
                fingerPrintFeatureSet, faceRecognitionFeatureSet, rfidScan, config);

        // Assert
        assertFalse(result);
    }

    @Test
    void combinedVerificationProcessing_Notifications() {
        // Arrange
        String fingerPrintFeatureSet = "validFingerPrintFeatureSet";
        String faceRecognitionFeatureSet = "validFaceRecognitionFeatureSet";
        String rfidScan = "validRfidScan";
        ArrayList<Boolean> config = new ArrayList<>(List.of(true, true, true));
        when(mockDataService.match(any(), any())).thenReturn(false);

        // Act
        boolean result = combinedVerificationSystem.combinedVerificationProcessing(
                fingerPrintFeatureSet, faceRecognitionFeatureSet, rfidScan, config);

        // Assert
        assertFalse(result);
        // Verify that the notifications are printed
}
}
