# CSE564-Team-6-SmartDoorLockSystem
This is the project for team 6 of the CSE 564 (Software Design) course. This project contains the design implementation of a simulation for a smart door lock system.
Prerequisite:
-------------
	Windows OS 

Kafka setup
------------
	Step 1: Unzip and Extract the Kafka folder in C:drive

	Step 2: (a) double click on the zookeeper.bat file and wait for it to load
			
			(b) once it is up and running, double click on the KafkaServer.bat file.
		    
		    	note* If the execution fails or closes unexpectedly, restart the kafka server again.
		
			(c) once both zookeeper and kafka server are running, double click on the kafkaTopicsStart.bat file.

if the batch files are not working then 

// to create a topic :
	
	step 1: goto the kafka folder in c: drive and open terminal:

	type the following command:

	.\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --topic notification 
	.\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --topic cms
	.\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --topic fingerprint
	.\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --topic rfid
	.\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --topic lockstate
	.\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --topic doorlockstate

// to start the topic
	step 2: goto kafka folder and open terminal:
			open a new terminal for each command
			type the following command.
	
	.\bin\windows\kafka-console-consumer.bat --topic notification --bootstrap-server localhost:9092
	.\bin\windows\kafka-console-consumer.bat --topic doorlockstate --bootstrap-server localhost:9092"
	.\bin\windows\kafka-console-consumer.bat --topic lockstate --bootstrap-server localhost:9092"
    .\bin\windows\kafka-console-consumer.bat --topic cms --bootstrap-server localhost:9092"
    .\bin\windows\kafka-console-consumer.bat --topic rfid --bootstrap-server localhost:9092"
	.\bin\windows\kafka-console-consumer.bat --topic fingerprint --bootstrap-server localhost:9092"


Program execution
------------------

	step 3:	goto CSE564-Team-6-SmartDoorLockSystem\src\main\java\com\smartdoor\project
			run the ProjectApplication file.

	
	Scenario 1: Add new User:
	--------------------------
		
			1.1 to test working Scenario - 
			--------------------------------  

			step 3.1:  when the projectApplication is started, enter the following inputs:
				
				- Enter the fingerprint - fingerPrintUser2
				- Face the camera: user2Photo
				- Place the rfid: user2RFID
				- User Management (Press "No" to skip)
				- Enter user management option(Type No to skip): add (predefined option)
				- Enter config(Type No to skip): {false,true,true}

			step 3.2 goto the notification terminal
					you will be able to see the message "user added successfully"

		
			1.2 to test failing scenario
			-----------------------------
			step 3.1: ===Restart the projectApplication, enter the following inputs:

				- Fingerprint: fingerPrintUser1 
				- Face Capture: Photo taken (user1Photo) 
				- RFID Registration: user1RFID	
 				- User Management (Press "No" to skip)
				  Enter user management option(Type No to skip): add
				- Configuration: (Press "No" to skip) 
				- Current Configuration: {False, True, True}


			step 3.2 goto the notification terminal
					you will be able to see the message "user already exists"



	Scenario 2: Update a new User:
	------------------------------
			step 3.1: Restart the projectApplication, enter the following inputs:

				- Fingerprint: fingerPrintUser1 
				- Face Capture: Photo taken (user1Photo) 
				- RFID Registration: user1RFID	
 				- User Management (Press "No" to skip)
				- Enter user id to update/delete - 550e8400-e29b-41d4-a716-446655440000
				- Option: update (predefined option)
				- Configuration: (Press "No" to skip) 
				- Current Configuration: {False, True, True}


			step 3.2 goto the notification terminal
					you will be able to see the message "uses updated successfully"



	Scenario 3: Delete an existing User
	------------------------------------
			step 3.1: Restart the ProjectApplication, enter the following inputs:

				- Fingerprint: fingerPrintUser1 
				- Face Capture: Photo taken (user1Photo) 
				- RFID Registration: user1RFID	
				- User Management (Press "No" to skip)
				- Enter user id to update/delete - 550e8400-e29b-41d4-a716-446655440000
				- Option: delete (predefined option)
				- Configuration: (Press "No" to skip) 
				- Current Configuration: {False, True, True}


			step 3.2 goto the notification terminal
					you will be able to see the message "uses deleted successfully"


	
	Scenario 4: Verifying an existing user for permissions and granting access
	---------------------------------------------------------------------------

			step 3.1: Restart the ProjectApplication, enter the following inputs:

				- Fingerprint: fingerPrintUser3 
				- Face Capture: Photo taken (user3Photo) 
				- RFID Registration: user3RFID	
				- User Management (Press "No" to skip): No
				- Enter config(Type No to skip): {true,true,true}


			step 3.2 goto the notification terminal
					you will be able to see the message "access granted"





	Scenario 5: Force Open the door to bypass verification System
	--------------------------------------------------------------

			step3.1: 	goto CSE564-Team-6-SmartDoorLockSystem\src\main\java\com\smartdoor\services
						
						run the LockStateMonitor.java file:



			step 3.2:   goto CSE564-Team-6-SmartDoorLockSystem\src\main\java\com\smartdoor\project
		
						run the ProjectApplication file


						step 3.2.1: check the lockstate and doorlockstate kafka topics  on the terminal
									you must be able to see true on both the terminals.
									when we start the project application we assume that both lockstate and doorlockstate are set to true

			step 3.3:	goto CSE564-Team-6-SmartDoorLockSystem\src\main\java\com\smartdoor\services
						
						run the SecurityBreachScenario.java file


						step 3.2.2: check the notification topic terminal:
									you will be able to view message "access breached"

						step 3.2.3	check the lockstate and doorlockstate kafka topics  on the terminal
									you must be able to see true on the lockstate topic terminal and false on the doorlockstate topic terminal.
									when the door is force opened, the internal lockstate and physical doorlockstate won't match. 
									we tried to mimic this scenario.


	Scenario 6,7: Camera angle feedback and light Intensity feedback
	----------------------------------------------------------------

			step 3.1:  goto CSE564-Team-6-SmartDoorLockSystem\src\main\java\com\smartdoor\project
		
					run the ProjectApplication file and type the following inputs:

					- Fingerprint: fingerPrintUser3 
					- Face Capture: Photo taken (user3Photo) 
					- RFID Registration: user3RFID	
					- User Management (Press "No" to skip): No
					- Enter config(Type No to skip): {true,true,true}


					you will be able to see the angle and intensity getting adjusted and the values are printed on the ProjectApplication Terminal


			step 3.2: check notification terminal:
					the message "access granted" will be displayed





		
  

		
