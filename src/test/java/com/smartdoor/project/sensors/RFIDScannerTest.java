package com.smartdoor.project.sensors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartdoor.models.Barcode;
import com.smartdoor.models.FeatureSet;
import com.smartdoor.services.KafkaProducerService;
import com.smartdoor.services.sensors.RFIDScanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RFIDScannerTest {

    private RFIDScanner rfidScanner;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rfidScanner = new RFIDScanner();
        rfidScanner.kafkaProducer = kafkaProducerService;
    }

    @Test
    void RFIDProcessor_ValidBarcode_ReturnsFeatureSet() throws Exception {
        // Arrange
        String barcodeValue = "barcode1";
        Barcode barcode = new Barcode();
        barcode.value = barcodeValue;

        FeatureSet expectedFeatureSet = new FeatureSet();
        expectedFeatureSet.value = "feature1";

        doNothing().when(kafkaProducerService).sendMessage(anyString(), anyString(), anyString());

        // Mocking JSON file read
        File mockFile = mock(File.class);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        JsonNode mockJsonNode = mock(JsonNode.class);
        when(mockFile.getAbsolutePath()).thenReturn("CSE564-Team-6-SmartDoorLockSystem/src/main/java/com/smartdoor/data/FingerPrintFeatureSetMap.json");
        when(mockObjectMapper.readTree(mockFile)).thenReturn(mockJsonNode);
        when(mockJsonNode.get(barcodeValue)).thenReturn(mock(JsonNode.class));
        when(mockJsonNode.get(barcodeValue).get("value")).thenReturn(mock(JsonNode.class));
        when(mockJsonNode.get(barcodeValue).get("value").asText()).thenReturn(expectedFeatureSet.value);

        rfidScanner.filePath = mockFile.getAbsolutePath();

        // Act
        FeatureSet result = rfidScanner.RFIDProcessor(false, barcode, 1);

        // Assert
        assertEquals(expectedFeatureSet.value, result.value);
        verify(kafkaProducerService, times(3)).sendMessage(anyString(), anyString(), anyString()); // 3 messages should be sent
    }

    @Test
    void RFIDProcessor_InvalidBarcode_ThrowsException() throws Exception {
        // Arrange
        String barcodeValue = "invalidBarcode";
        Barcode barcode = new Barcode();
        barcode.value = barcodeValue;

        doNothing().when(kafkaProducerService).sendMessage(anyString(), anyString(), anyString());

        // Mocking JSON file read
        File mockFile = mock(File.class);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        JsonNode mockJsonNode = mock(JsonNode.class);
        when(mockFile.getAbsolutePath()).thenReturn("CSE564-Team-6-SmartDoorLockSystem/src/main/java/com/smartdoor/data/FingerPrintFeatureSetMap.json");
        when(mockObjectMapper.readTree(mockFile)).thenReturn(mockJsonNode);
        when(mockJsonNode.get(barcodeValue)).thenReturn(null);

        rfidScanner.filePath = mockFile.getAbsolutePath();

        // Act and Assert
        assertEquals("data not found", org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> {
            rfidScanner.RFIDProcessor(false, barcode, 1);
        }).getMessage());
        verify(kafkaProducerService, times(2)).sendMessage(anyString(), anyString(), anyString()); // 2 messages should be sent
    }
}