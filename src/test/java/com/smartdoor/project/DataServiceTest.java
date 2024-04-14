package com.smartdoor.project;

import com.smartdoor.services.DataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DataServiceTest {

    private DataService dataService;

    @BeforeEach
    void setUp() {
        dataService = new DataService();
    }

    @Test
    void matchCorrectInputTest() {
        // Arrange
        String input = "1000101";
        String scanType = "rfid_scan";

        // Act
        boolean result = dataService.match(input, scanType);

        // Assert
        assertTrue(result);
    }

    @Test
    void matchIncorrectInputTest() {
        // Arrange
        String input = "1111111"; // A value that does not exist in the mock data
        String scanType = "rfid_scan";

        // Act
        boolean result = dataService.match(input, scanType);

        // Assert
        assertFalse(result);
    }

    @Test
    void matchIncorrectScanTypeTest() {
        // Arrange
        String input = "1000101";
        String scanType = "invalid_scan_type"; // An invalid scan type

        // Act
        boolean result = dataService.match(input, scanType);

        // Assert
        assertFalse(result);
    }
}
