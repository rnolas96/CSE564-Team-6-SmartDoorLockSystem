package com.smartdoor.services;

import com.smartdoor.models.Notification;
import com.smartdoor.project.ProjectApplication;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class LockStateMonitor {

    boolean breached = false;

    private static final String BOOTSTRAP_SERVERS = "localhost:9092"; // Replace with your Kafka broker address
    private static final String LOCKED_STATE_TOPIC = "lockstate";
    private static final String DEADBOLT_LOCKSTATE_TOPIC = "doorlockstate";

    String keySerializer = StringSerializer.class.getName();
    String valueSerializer = StringSerializer.class.getName();
    String notificationTopic = "notification";
    NotificationService notificationService = new NotificationService("localhost:9092",StringSerializer.class.getName(),StringSerializer.class.getName());    private String latestLockedState = null;
    private String latestDeadboltState = null;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(LockStateMonitor.class, args);

        LockStateMonitor monitor = new LockStateMonitor();
        monitor.startMonitoring();
    }

    public void startMonitoring() throws InterruptedException {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest"); // Start consuming from the end

        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(LOCKED_STATE_TOPIC, DEADBOLT_LOCKSTATE_TOPIC));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
            for (ConsumerRecord<String, String> record : records) {
                String topic = record.topic();
                String value = record.value();



                if (topic.equals(LOCKED_STATE_TOPIC)) {
                    latestLockedState = value;
                } else if (topic.equals(DEADBOLT_LOCKSTATE_TOPIC)) {
                    latestDeadboltState = value;
                }

                if(latestLockedState != null && latestDeadboltState != null && latestLockedState.equals(latestDeadboltState) && breached ==true){
                    breached = false;
                    Notification notification = new Notification();
                    notification.message = "system restored";
                    notification.type  = "alert";
                    notification.topic = notificationTopic;
                    notificationService.notify(notification);

                    System.out.println("system restored");
                }

                // Check for state mismatch and print message
                if (latestLockedState != null && latestDeadboltState != null && !latestLockedState.equals(latestDeadboltState)) {
                    breached = true;
                    System.out.println("locked state"+latestLockedState);
                    System.out.println("deadbolt lock state"+latestDeadboltState);

                    Notification notification = new Notification();
                    notification.message = "access breached";
                    notification.type  = "alert";
                    notification.topic = notificationTopic;
                    notificationService.notify(notification);
                    System.out.println("Entry Breached! Locked state: " + latestLockedState + ", Deadbolt state: " + latestDeadboltState);

                }

                if(latestLockedState != null && latestDeadboltState != null && latestLockedState.equals(latestDeadboltState)) {
                    latestLockedState = null;
                    latestDeadboltState = null;
                }
            }
        }
    }
}