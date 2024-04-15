package com.smartdoor.project;


import com.smartdoor.models.AdminControlSystemOutput;
import com.smartdoor.models.User;
import com.smartdoor.services.AdminControlSystem;
import com.smartdoor.services.DataService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminControlSystemTest {

    private AdminControlSystem adminControlSystem;

    @Mock
    private DataService mockDataService;

    @BeforeEach
    void setUp() {
        mockDataService = mock(DataService.class);
        adminControlSystem = new AdminControlSystem();
        adminControlSystem.dataService = mockDataService;
    }

    @Test
    void adminControlProcessing_UserNotFound_AddUser() {
        // Arrange
        String fingerPrintFeatureSet = "fingerprint123";
        String faceRecognitionFeatureSet = "faceRecognition123";
        String rfidScan = "rfid123";
        boolean accessState = false;
        UUID userId = UUID.randomUUID();
        ArrayList<Boolean> config = new ArrayList<>() {{
            add(true);
            add(true);
            add(true);
        }};
        String option = "add";

        when(mockDataService.match(fingerPrintFeatureSet, "fingerprint_scan")).thenReturn(false);
        when(mockDataService.match(faceRecognitionFeatureSet, "face_recognition")).thenReturn(false);
        when(mockDataService.match(rfidScan, "rfid_scan")).thenReturn(false);

        // Act
        AdminControlSystemOutput output = adminControlSystem.adminControlProcessing(fingerPrintFeatureSet, faceRecognitionFeatureSet, rfidScan, accessState, userId, config, option);

        // Assert
        assertTrue(output.accessState); // Access should be granted after adding user
        assertEquals(config, output.config); // Configuration should remain unchanged
        verify(mockDataService).match(fingerPrintFeatureSet, "fingerprint_scan");
        verify(mockDataService).match(faceRecognitionFeatureSet, "face_recognition");
        verify(mockDataService).match(rfidScan, "rfid_scan");
    }

    @Test
    void adminControlProcessing_UserFound_DeleteUser() {
        // Arrange
        String fingerPrintFeatureSet = "fingerprint123";
        String faceRecognitionFeatureSet = "faceRecognition123";
        String rfidScan = "rfid123";
        boolean accessState = false;
        UUID userId = UUID.randomUUID();
        ArrayList<Boolean> config = new ArrayList<>() {{
            add(true);
            add(true);
            add(true);
        }};
        String option = "delete";

        when(mockDataService.match(fingerPrintFeatureSet, "fingerprint_scan")).thenReturn(true);
        when(mockDataService.match(faceRecognitionFeatureSet, "face_recognition")).thenReturn(true);
        when(mockDataService.match(rfidScan, "rfid_scan")).thenReturn(true);

        // Act
        AdminControlSystemOutput output = adminControlSystem.adminControlProcessing(fingerPrintFeatureSet, faceRecognitionFeatureSet, rfidScan, accessState, userId, config, option);

        // Assert
        assertFalse(output.accessState); // Access should be denied after deleting user
        assertEquals(config, output.config); // Configuration should remain unchanged
        verify(mockDataService).match(fingerPrintFeatureSet, "fingerprint_scan");
        verify(mockDataService).match(faceRecognitionFeatureSet, "face_recognition");
        verify(mockDataService).match(rfidScan, "rfid_scan");
    }

    @Test
    void adminControlProcessing_UserFound_UpdateUser() {
        // Arrange
        String fingerPrintFeatureSet = "fingerprint123";
        String faceRecognitionFeatureSet = "faceRecognition123";
        String rfidScan = "rfid123";
        boolean accessState = false;
        UUID userId = UUID.randomUUID();
        ArrayList<Boolean> config = new ArrayList<>() {{
            add(true);
            add(true);
            add(true);
        }};
        String option = "update";

        when(mockDataService.match(fingerPrintFeatureSet, "fingerprint_scan")).thenReturn(true);
        when(mockDataService.match(faceRecognitionFeatureSet, "face_recognition")).thenReturn(true);
        when(mockDataService.match(rfidScan, "rfid_scan")).thenReturn(true);

        // Act
        AdminControlSystemOutput output = adminControlSystem.adminControlProcessing(fingerPrintFeatureSet, faceRecognitionFeatureSet, rfidScan, accessState, userId, config, option);

        // Assert
        assertTrue(output.accessState); // Access should be granted after updating user
        assertEquals(config, output.config); // Configuration should remain unchanged
        verify(mockDataService).match(fingerPrintFeatureSet, "fingerprint_scan");
        verify(mockDataService).match(faceRecognitionFeatureSet, "face_recognition");
        verify(mockDataService).match(rfidScan, "rfid_scan");
    }
}
