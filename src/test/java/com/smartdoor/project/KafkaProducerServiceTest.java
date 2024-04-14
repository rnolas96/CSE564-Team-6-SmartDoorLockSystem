package com.smartdoor.project;

import com.smartdoor.services.KafkaProducerService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class KafkaProducerServiceTest {

    private KafkaProducer<String, String> mockProducer;
    private KafkaProducerService kafkaProducerService;

    @BeforeEach
    void setUp() {
        mockProducer = mock(KafkaProducer.class);
        kafkaProducerService = new KafkaProducerService("localhost:9092", "org.apache.kafka.common.serialization.StringSerializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProducerService.producer = mockProducer;
    }

    @Test
    void sendMessageTest() {
        // Arrange
        String topic = "test-topic";
        String key = "test-key";
        String value = "test-value";

        // Act
        kafkaProducerService.sendMessage(topic, key, value);

        // Assert
        ArgumentCaptor<ProducerRecord<String, String>> argument = ArgumentCaptor.forClass(ProducerRecord.class);
        verify(mockProducer).send(argument.capture());
        ProducerRecord<String, String> capturedRecord = argument.getValue();
        assertEquals(topic, capturedRecord.topic());
        assertEquals(key, capturedRecord.key());
        assertEquals(value, capturedRecord.value());
    }

    @Test
    void closeTest() {
        // Act
        kafkaProducerService.close();

        // Assert
        verify(mockProducer).close();
    }
}
