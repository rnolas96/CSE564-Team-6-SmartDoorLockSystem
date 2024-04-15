package com.smartdoor.Actuator;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;

public class DeadBolt {
    private boolean deadboltSensorLockedState;

    String topic = "doorlockstate";
    String infoKey = "Info";
    String errorKey = "Error";
    String value;

    String keySerializer = StringSerializer.class.getName();
    String valueSerializer = StringSerializer.class.getName();
    DeadBoltKafkaProducer deadBoltKafkaProducer = new DeadBoltKafkaProducer("localhost:9092",StringSerializer.class.getName(),StringSerializer.class.getName());


    public void setDeadboltSensorLockedState(boolean lockedState) {
        this.deadboltSensorLockedState = lockedState;
        deadBoltKafkaProducer.produceMessage(topic,infoKey,String.valueOf(this.deadboltSensorLockedState));

    }

    public boolean getDeadboltSensorLockedState() {
        return deadboltSensorLockedState;
    }



}
