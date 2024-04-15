package com.smartdoor.services.sensors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartdoor.exceptions.UnauthorizedException;
import com.smartdoor.models.Barcode;
import com.smartdoor.models.FeatureSet;
import com.smartdoor.models.Notification;
import com.smartdoor.services.KafkaProducerService;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.File;

public class RFIDScanner {
	String topic = "rfid";
	String infoKey = "Info";
	String errorKey = "Error";
	String value;

	String keySerializer = StringSerializer.class.getName();
	String valueSerializer = StringSerializer.class.getName();

	public KafkaProducerService kafkaProducer = new KafkaProducerService("localhost:9092",StringSerializer.class.getName(),StringSerializer.class.getName());
	public RFIDScanner(){

		value = "rifd scanner initialized";
		kafkaProducer.sendMessage(topic, infoKey, value);
	}

	private boolean captured;

	private FeatureSet scanned;

	private Barcode RFIDScan;

	private Notification Notification;

	private int c_id;

	private FeatureSet finalScan;

	public String filePath = "src/main/java/com/smartdoor/data/RFIDFeatureMap.json";

	private boolean checkScanned(boolean scanned) {
		return false;
	}

	private FeatureSet getFeatureSet(Barcode RFIDScan) throws Exception {
		// Read the JSON file

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(new File(filePath));

		JsonNode rfid = rootNode.get(RFIDScan.value);

		if (rfid != null) {

			String rfidValue = rfid.get("value").asText();

			value = "success: RFID FeatureSet = "+rfidValue.toString();
			kafkaProducer.sendMessage(topic, infoKey, value);

			FeatureSet featureSet = new FeatureSet();
			featureSet.value = rfidValue;

			return featureSet;

		}
		else{
			value = "RFID data not found";
			kafkaProducer.sendMessage(topic, errorKey, value);

			throw new UnauthorizedException("rfid data not found",401);
		}

	}

	public FeatureSet RFIDProcessor(boolean captured, Barcode RFIDScan, int cf_id) throws Exception {
		while(!captured){
			try {
				scanned = getFeatureSet(RFIDScan);
				captured = true;
			}

			catch (UnauthorizedException ex) {
				if (ex.getStatusCode() == 401) {
					//send Notification

					value = "rfid access unnauthorized";
					kafkaProducer.sendMessage(topic,errorKey,value);

					return scanned;
				}
			}

			catch (Exception ex){

				value = "error occured while getting rfid featureset";
				kafkaProducer.sendMessage(topic,errorKey,value);

				throw new Exception("error occurred while getting rfid featureset" +  ex.getMessage());

			}
		}

		finalScan = scanned;
		return finalScan;
	}



}
