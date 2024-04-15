package com.smartdoor.services;

import com.smartdoor.models.Notification;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class NotificationService {
    private final String bootstrapServers;
    private final String keySerializer;
    private final String valueSerializer;
    public KafkaProducer<String, String> producer;

    public NotificationService(String bootstrapServers, String keySerializer, String valueSerializer) {
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
    public void notify(Notification notification) {
        ProducerRecord<String, String> record = new ProducerRecord<>(notification.topic, notification.type,notification.message);
        producer.send(record);
    }





}
