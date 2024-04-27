package com.smartdoor.services;

import com.smartdoor.Actuator.DeadBoltKafkaProducer;
import com.smartdoor.models.Barcode;
import com.smartdoor.models.Fingerprint;
import com.smartdoor.project.ProjectApplication;
import com.smartdoor.services.*;
import com.smartdoor.services.sensors.FingerprintScanner;
import com.smartdoor.services.sensors.RFIDScanner;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.locks.Lock;

public class SecurityBreachScenario {
    public static void main(final String[] args) throws Exception {

        String topic = "doorlockstate";
        String infoKey = "Info";
        String errorKey = "Error";
        String value;

        String keySerializer = StringSerializer.class.getName();
        String valueSerializer = StringSerializer.class.getName();





        SpringApplication.run(SecurityBreachScenario.class, args);
        // scenario to test auth breach
        DoorLockKafkaProducer doorLockKafkaProducer = new DoorLockKafkaProducer();
        DeadBoltKafkaProducer deadBoltKafkaProducer = new DeadBoltKafkaProducer("localhost:9092",StringSerializer.class.getName(),StringSerializer.class.getName());

        doorLockKafkaProducer.produceMessage(true);

        deadBoltKafkaProducer.produceMessage(topic,infoKey,String.valueOf(false));




    }
}
