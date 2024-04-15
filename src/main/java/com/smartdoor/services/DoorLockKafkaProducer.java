package com.smartdoor.services;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class DoorLockKafkaProducer {
    private static final String TOPIC_NAME = "lockstate";
    public  org.apache.kafka.clients.producer.KafkaProducer<String, String> producer;


    public  void produceMessage(boolean lockedState) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        // Add other configurations as needed (e.g., acks, retries)

        producer = new org.apache.kafka.clients.producer.KafkaProducer<>(properties);
        producer.send(new ProducerRecord<>(TOPIC_NAME, "currentlockstate", String.valueOf(lockedState)));




    }

}
