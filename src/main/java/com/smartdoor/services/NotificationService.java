package com.smartdoor.services;

import com.smartdoor.models.Notification;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    String topic = "notification";

    String keySerializer = StringSerializer.class.getName();
    String valueSerializer = StringSerializer.class.getName();

    KafkaProducerService kafkaProducer = new KafkaProducerService("localhost:9092",StringSerializer.class.getName(),StringSerializer.class.getName());

    public void notify(Notification notification){
        kafkaProducer.sendMessage(topic,notification.message,notification.type);
    }
}
