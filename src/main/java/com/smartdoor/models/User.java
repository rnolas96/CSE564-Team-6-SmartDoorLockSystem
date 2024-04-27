package com.smartdoor.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartdoor.exceptions.AddUserException;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class User {

	private int userId;

	public void addUser(String fingerPrintFeatureSet, String faceRecognitionFeatureSet, String rfidScan) throws IOException {
		// mocking addition of data for new user
		try {
			System.out.println("adding user");

		}

		catch(AddUserException ex) {
			throw new AddUserException("unable to add user", 404);
		}
	}

	public void updateUser(String fingerPrintFeatureSet, String faceRecognitionFeatureSet, String rfidScan) {
		// mocking updation of data for new user
	}

	public void deleteUser(UUID user_id) {
		// mocking deletion of data for new user
	}

}
