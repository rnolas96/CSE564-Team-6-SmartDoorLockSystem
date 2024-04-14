package com.smartdoor.project.sensors;

import com.smartdoor.models.FeatureSet;
import com.smartdoor.models.Fingerprint;
import com.smartdoor.services.sensors.FingerprintScanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

public class FingerprintScannerTest {

    private FingerprintScanner fingerprintScanner;
    private Fingerprint mockFingerprint;

    @BeforeEach
    void setUp() {
        fingerprintScanner = new FingerprintScanner();
        mockFingerprint = mock(Fingerprint.class);
    }

    @Test
    void biometricProcessor_Captured_ReturnsFeatureSet() throws Exception {
        // Arrange
        boolean captured = true;
        String fingerprintValue = "sampleFingerprintValue";
        when(mockFingerprint.getValue()).thenReturn(fingerprintValue);

        // Act
        FeatureSet result = fingerprintScanner.biometricProcessor(captured, mockFingerprint, 1);

        // Assert
        assertNotNull(result);
        assertEquals(fingerprintValue, result.value);
    }

    @Test
    void biometricProcessor_ThrowsException() throws Exception {
        // Arrange
        boolean captured = true;
        String errorMessage = "error occurred while getting featureset";
        when(mockFingerprint.getValue()).thenReturn("sampleFingerprintValue");
        when(fingerprintScanner.getFeatureSet(mockFingerprint)).thenThrow(new Exception(errorMessage));

        // Act and Assert
        Exception exception = assertThrows(Exception.class, () -> {
            fingerprintScanner.biometricProcessor(captured, mockFingerprint, 1);
        });

        assertEquals(errorMessage, exception.getMessage());
    }
}