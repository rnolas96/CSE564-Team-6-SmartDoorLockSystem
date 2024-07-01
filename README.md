# ASU CSE 564-Team-6-SmartDoorLockSystem
This is the project for Team 6 of the CSE 564 (Software Design) course. The project aims to develop an advanced Smart Door Lock System incorporating multiple biometric authentication methods, including RFID sensor technology, fingerprint recognition and face recognition. The system enhances security and convenience, providing a seamless and multilayered approach to access control.

### This project contains the design implementation of a simulation for a smart door lock system.

## Assumptions

We have three main assumptions in our project -
- In our system, the smart door is allowed to handle only one user at a time.
- We have a single door that responds to the processing that happens within the system
- Admin is the only person that has the power to add, update and delete users

## Cyber-Physical System features 

- **Reactive** - Our Smart door system is a reactive system as it can handle different kinds of
input from the user and give different responses accordingly. Some of these different
inputs are the inputs provided by the user in the form of the three sensors as well as the
input provided by the admin.
- **Real-time** - Our Smart door system is a real-time system as it continuously listens for
input and responds quickly to the requirement of the situation. Information is not stored
and responded to, at a later time. The unlocking of the door happens as soon as the input
is received.
- **Concurrency** - Concurrency happens between the input consumption and processing
phase of all 3 sensors. The feature set needs to be generated for the inputs from the
sensors. This featureset is matched with the DB to confirm whether it is an existing user.
This processing takes time and is done concurrently for all the sensor inputs.
- **Feedback** - The face recognition sensor uses feedback to judge the environment and
reset itself in a manner that allows it to consume the image of the user in front of it. If it
isn’t able to generate a featureset from the image, then it recaptures the image after
recorrecting itself. This process keeps happening until the featureset is generated. Our
system also takes feedback from the deadbolt to evaluate the locked state. If there is a
mismatch between the two states (the physical and software state), then it is equivalent
to a hazard state. The deadbolt has gone to an unlocked state without the knowledge of
the user.

## System Design

Block Diagram:
-------------

Our project mainly comprises 3 scanners for getting user credentials like fingerprint, face and rfid
scan, a Central Management System and other supporting services as shown in the block
diagram

![image](https://github.com/rnolas96/CSE564-Team-6-SmartDoorLockSystem/assets/53873549/ee8bad40-9cf9-4de6-8c10-800d4d361dfd)

Composite Synchronous Reactive Component Diagram:
-------------

Overview of the Composite SRC -
- The left side 3 SRCs are for Fingerprint Scanner, Face Recognition Scanner and RFID
Scanner respectively.
- Outputs of all scanners go into the Central Management System for processing and
verification
- Central Management System lies on middle part right side of the composite SRC
- Below Central Management System we have Door Lock Mechanism SRC with
Deadbolt sensor
- notification and logged_data is produced as output from every SRC and goes into the
Logging Service and Notification System on the top right side of the Composite SRC
diagram.
- We also have blocks for Display, Power Management System that in itself can become
composite SRCs and are out of the scope of this project.

![image](https://github.com/rnolas96/CSE564-Team-6-SmartDoorLockSystem/assets/53873549/dd7c2f5e-eee6-4823-8c9d-96f2ca9ff806)

Composite UML Class Diagram:
-------------

![image](https://github.com/rnolas96/CSE564-Team-6-SmartDoorLockSystem/assets/53873549/4872dafd-11a0-4072-b92f-f42ec32bdb5e)


## Implementation

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


## Frameworks and Feedback Tools

List of all things (e.g., Astah, data sources, etc.) that are used for designing, coding, testing,
experimenting, and evaluating the course project.

### Designing
- Astah - Astah was used for making our UML diagrams
- Draw.io - SRCs and other diagrams were made using draw.io

### Coding
- Java/Maven/Spring Boot - We used a Spring Boot Maven project to build our system.
  
### Testing
- Junit - We had Junit tests for most of our important components in order to make sure
that all the processing was happening as expected.

### Experimenting
- Kafka - Monitors and listeners to display and view our different scenarios and allow us
to conduct proper experimentation.

## Conclusions

Lessons learned:
-----------

Developing a smart door lock system with multiple user input mechanisms and central
management has provided valuable insights into the integration of advanced software design
concepts and CPS features. Here's what we've learned from the project:

- **Software Design:** Utilizing UML classes and SRCs to create a comprehensive software
design helped visualize the relationships between various components, such as
fingerprint scanners, face recognition scanners, RFID scanners, central management
systems, door lock mechanisms, and deadbolt sensors. This approach of combining
formal and semi-formal methods of software design allowed for better planning and
integration of software components.
- **Comprehensive Security:** Incorporating fingerprint scanning, face recognition, and
RFID scanning significantly enhances security. These methods offer a multi-layered
approach, reducing the chances of unauthorized access.
- **User Experience Matters:** A seamless user experience is crucial. Ensuring the
biometric scanners and RFID systems operate quickly and efficiently improves user
satisfaction. Delays or errors can undermine confidence in the system.
- **Central Management System:** A centralized control system streamlines management
and monitoring. It provides administrators with a single point to manage access, review
logs, and respond to incidents. However, security measures must be in place to protect
the central system itself from breaches.
- **Reliability and Redundancy:** The inclusion of a deadbolt sensor provides redundancy
and enhances reliability. In case of electronic failure, the deadbolt offers a manual
backup. Ensuring this manual override is easy to use but secure is essential for building
user trust.
- **Integration and Compatibility:** Integrating the various components of the system
(scanners, door lock mechanisms, and central management) demands careful planning.
Ensuring compatibility among hardware and software components is key to a smooth
operation.
- **Testing and Quality Assurance:** Thorough testing is critical to identify and address
potential vulnerabilities. This includes testing individual components and the system as a
whole under various conditions.
- **Scalability and Maintenance:** Designing the system with scalability in mind ensures it
can grow with changing needs. Regular maintenance and software updates are required
to keep the system secure and functional.


Recommendations:
-----------
Based on the lessons learned from developing a smart door lock system that integrates various
user input mechanisms, a central management system, and software design using SRCs and
UML standards, the following recommendations are made for future projects:

- **Embrace Modularity:** Design the system with a modular structure. This approach not
only enhances flexibility but also makes it easier to maintain and scale the system over
time. Use UML class diagrams to create clear boundaries between different software
components, facilitating updates and troubleshooting.
- **Prioritize Security:** Security should be a core focus throughout the development
process. Implement robust encryption methods and secure storage solutions to protect
sensitive data, especially biometric information. Use UML activity diagrams to map out
security processes and ensure compliance with data protection regulations.
- **Develop Comprehensive Use Cases:** Create detailed use cases and user stories to
define system functionality and user interactions. This helps align software design with
user expectations and ensures that the system meets security requirements. It also
provides a clear foundation for testing and quality assurance.
- **Leverage UML Diagrams for Clarity:** Use a variety of UML diagrams, including
sequence, state, and component diagrams, to visualize the system's structure and
workflows. This helps identify potential issues, optimize data flow, and improve overall
system performance.
- **Ensure Compatibility and Integration:** Design interfaces and communication
protocols that allow seamless integration between system components and the central
management system. UML component diagrams can guide this process and reduce
compatibility issues.
- **Provide Backup and Redundancy:** Include backup mechanisms, such as deadbolt
sensors, to ensure the system remains functional in the event of electronic failure.
Design these features to be user-friendly but secure, maintaining the overall security of
the system.
- **Focus on User Experience:** A seamless and efficient user experience is critical. Ensure
that biometric scanners and RFID systems operate quickly and without errors to
maintain user confidence. Test and refine these components to minimize delays and false
positives.
- **Regularly Update and Maintain:** Plan for ongoing maintenance and software updates
to keep the system secure and functional. This includes regular security patches,
firmware updates, and system performance optimizations.

### By following these recommendations, future projects can create more secure, reliable, and user-friendly smart door lock systems. The integration of SRCs and UML standards provides a solid framework for software design, while focusing on security and user experience ensures a successful outcome.





		
  

		
