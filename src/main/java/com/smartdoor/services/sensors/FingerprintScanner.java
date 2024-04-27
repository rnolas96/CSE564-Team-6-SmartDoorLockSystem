package com.smartdoor.services.sensors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartdoor.exceptions.UnauthorizedException;
import com.smartdoor.models.FeatureSet;
import com.smartdoor.models.Fingerprint;
import com.smartdoor.models.Notification;
import com.smartdoor.services.KafkaProducerService;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FingerprintScanner {

	private boolean captured;

	private FeatureSet scanned;

	private Notification notification;

	private Fingerprint Scan;

	private int c_id;

	private FeatureSet finalScan;

	String filePath = "src/main/java/com/smartdoor/Data/FingerPrintFeatureSetMap.json";

	String topic = "fingerprint";
	String infoKey = "Info";
	String errorKey = "Error";
	String value;

	String keySerializer = StringSerializer.class.getName();
	String valueSerializer = StringSerializer.class.getName();

	KafkaProducerService kafkaProducer = new KafkaProducerService("localhost:9092",StringSerializer.class.getName(),StringSerializer.class.getName());

	public FeatureSet getFeatureSet(Fingerprint scan) throws Exception {


		// Read the JSON file
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(new File(filePath));

		JsonNode fingerprint = rootNode.get(scan.value);

		if (fingerprint != null) {
			String fingerprintValue = fingerprint.get("value").asText();

			value = "Finger Print FeatureSet = "+fingerprintValue.toString();
			kafkaProducer.sendMessage(topic, infoKey, value);

			FeatureSet featureSet = new FeatureSet();
			featureSet.value = fingerprintValue;

			return featureSet;
		}
		else{
			System.out.println("fingerprint data not found");
			value = "fingerprint data not found";
			kafkaProducer.sendMessage(topic,errorKey,value);

			throw new UnauthorizedException("rfid data not found",401);
		}

	}

	public FeatureSet biometricProcessor(boolean captured, Fingerprint scan, int c_id) throws Exception {

		while(!captured){
			try {
				scanned = getFeatureSet(scan);
				captured = true;
			}
			catch (UnauthorizedException ex) {
				if (ex.getStatusCode() == 401) {
					//send Notification

					value = "fingerprint access unnauthorized";
					kafkaProducer.sendMessage(topic,errorKey,value);
					return scanned;
				}
			}
			catch (Exception ex){
				value = "error occured while getting fingerprint featureset";
				kafkaProducer.sendMessage(topic,errorKey,value);

				System.out.println("error occurred while getting fingerprint featureset" +  ex.getMessage());
				return new FeatureSet();


			}
		}

		finalScan = scanned != null ? scanned : new FeatureSet(); // Return an empty FeatureSet if scanned is null
		return finalScan;

	}

}
