package com.smartdoor.services;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaProducerService {

    private final String bootstrapServers;
    private final String keySerializer;
    private final String valueSerializer;
    private final KafkaProducer<String, String> producer;

    public KafkaProducerService(String bootstrapServers, String keySerializer, String valueSerializer) {
        this.bootstrapServers = bootstrapServers;
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        // Add other configurations as needed (e.g., acks, retries)
        producer = new KafkaProducer<>(props);
    }

    public void sendMessage(String topic, String key, String value) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        producer.send(record);
    }

    // Close the producer when finished (optional)
    public void close() {
        producer.close();
    }
}