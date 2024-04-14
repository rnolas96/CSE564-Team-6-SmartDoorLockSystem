package com.smartdoor.services.sensors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartdoor.models.Barcode;
import com.smartdoor.models.FeatureSet;
import com.smartdoor.models.Notification;
import com.smartdoor.services.KafkaProducerService;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.File;

public class RFIDScanner {
	String topic = "rfidService";
	public RFIDScanner(){


		String key = "Info";
		String value = "rifd scanner initialized";

		kafkaProducer.sendMessage(topic, key, value);
	}
	String keySerializer = StringSerializer.class.getName();
	String valueSerializer = StringSerializer.class.getName();

	KafkaProducerService kafkaProducer = new KafkaProducerService("localhost:9092",StringSerializer.class.getName(),StringSerializer.class.getName());

	private boolean captured;

	private FeatureSet scanned;

	private Barcode RFIDScan;

	private Notification Notification;

	private int c_id;

	private FeatureSet finalScan;

	String filePath = "CSE564-Team-6-SmartDoorLockSystem/src/main/java/com/smartdoor/data/RFIDFeatureMap.json";

	private boolean checkScanned(boolean scanned) {
		return false;
	}

	private FeatureSet getFeatureSet(Barcode RFIDScan) throws Exception {
		// Read the JSON file

		String key = "Info";
		String value = "getFeatureset invoked";

		kafkaProducer.sendMessage(topic, key, value);
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(new File(filePath));

		JsonNode rfid = rootNode.get(RFIDScan.value);

		if (rfid != null) {

			String rfidValue = rfid.get("value").asText();
			key = "Info";
			value = "RFID FeatureSet = "+rfidValue.toString();

			kafkaProducer.sendMessage(topic, key, value);

			FeatureSet featureSet = new FeatureSet();
			featureSet.value = rfidValue;

			return featureSet;

		}
		else{
			key = "Error";
			value = "RFID data not found";
			kafkaProducer.sendMessage(topic, key, value);


			throw new Exception("data not found");
		}

	}

	public FeatureSet RFIDProcessor(boolean captured, Barcode RFIDScan, int cf_id) throws Exception {
		while(!captured){
			try {
				scanned = getFeatureSet(RFIDScan);
				captured = true;
			}
			catch (Exception ex){
				throw new Exception("error occurred while getting featureset" +  ex.getMessage());

			}
		}

		finalScan = scanned;
		return finalScan;
	}



}
