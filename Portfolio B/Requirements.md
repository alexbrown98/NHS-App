
# Portfolio B

## Requirements
### List of identified system stakeholders

![img_stakeholders](.images/stakeholdersmap.png)


| **Stakeholder** | **Desription** |
|--|--|
|Doctors  | As a doctor, I would like to easily be able to organise and receive feedback from my Teaching Sessions so that I can add it to my portfolio. I want an easy-to-use application where I can create and edit sessions while also being able to communicate with students when needed to avoid scheduling problems.|
| Students	| As a student, I want greater choice over the topics of Bedside Teaching I receive in hospitals. This would allow me to improve my knowledge over a variety of different topics while also making the teaching experience more enjoyable. I want to be able to communicate with Doctors if i cannot make attend a session, while also being contacted by Doctors when they themselves attend. I would also like to be able to book sessions in advance, change sessions, and view upcoming sessions.|
| Client | As the client, my idea is to create a more efficient and enjoyable environment for both doctors and students so that they can carry out in-hospital teaching in a more gratifying way. I want teaching sessions to be easily created and edited, in a way that all members involved are promptly notified about any changes. I also want to make available the communication between doctors and students which could make scheduling problems more easily.
|Patients | As a patient, I don’t really have much interest in the project, apart from the fact that it might lead to a less hectic environment in the hospital rooms during teaching. Moreover, I don’t want any of my confidential data being used in an insecure way.
| Hospital Management | As part of the Hospital Management team, my goal is to achieve a less chaotic atmosphere in the hospital that will increase productivity of hospital staff, while creating a more peaceful environment for patients.
| Hospital Tech Team | As part of the Hospital Tech Team, I am in charge of IT in the hospital and have access to confidential data of both doctors and patients. I would prefer that the project would require as little information and resources from our servers while also keeping any data secure.
|Doctor Secretaries | As a secretary of a busy doctor, the current method of creating teaching sessions is very inefficient and often leads to mistakes. I would like an application that could make the creation and editing of sessions intuitive, quick and easy, so that I can more effectively manage the time and activities of the doctor.
| Project Team | As part of the project team, my goal is to create and deliver the best possible version of the application that my client has in mind. I want to work as closely as possible with all system stakeholders so that I can create a successful application. I would like to work in an Agile fashion while making sure any implementation is both functioning and secure. I want to have constant communication with both the client and the rest of the team so that we can be sure to be on trach at all times.
|Lecturers | As lecturers, we want students to demonstrate good communication skills with clients and themselves, while also creating a good product.


##

### User Stakeholders

 - **Doctors / Secretaries**: 
	 - _Doctor Create-Session Story_: As a Doctor, I want to create a teaching session on a specific date and time, describing the material that will be taught. This session will be available for medical students to book, so that sessions are sufficiently attended.
	 - *Doctor Receive-Feedback Story*: As a Doctor, I want to receive feedback for the teaching I do at the hospital so I can showcase it in a portfolio.
	 - Doctor Communication Story: As a Doctor, I want to be able to communicate with students so that we can more easily solve scheduling problems while also being allowed to share teaching resources.
 - **Students**
	 -   *Student Book-Session Story*: As a student, I want to be able to choose which sessions I attend and when I attend them so that I can more easily fit them into my busy timetable while also making the sessions more interesting.
	 - *Student Give-Feedback Story*: As a student, I want to leave feedback for Doctors so I can communicate what I liked and what I didn't like, so that the constructive criticism can lead to an improvement in the teaching sessions.
	 - *Student Current-Sessions Story*: As a student, I want to be able to view my upcoming sessions while also being able to un-book a session if something comes up, so that other interested students can take my place.

##

### Flow Steps


  - Doctor Create-Session Story:
	- Basic Flow:
		 1. Login to application
		 2. Select “Create Session”
		 3. Choose date, time, and topic (possibly other parameters such as spaces available, location, session length etc.).
		 4. Publish session so students can book it.
		 5. Receive notifications about booking and/or cancelations.
	- Alternative Flow:
		 1. Login to application
		 2. Select "Create Session"
		 3. Choose date, time, and topic
		 4. Public session so students can book it.
		 5. After a change of plans, go back to "My Sessions" and edit some details about the session.
	- Exceptional Flow:
		1. Login to application
		2. Error when loging in, contact IT team to hopefuly fix the problem.
 -   Doctor Communicate Story:
	   - Basic Flow:
		    1. Login to application	
		    2. Select “ My Sessions”
		    3. View attending students for each session
		    4. Send a general message to the whole group of attending students.
		    5. Receive notifications about responses 
		- Alternative Flow:
			1. Login to application
			2. Select "My Sessions"
			3. View attending students for each session
			4. Select a specific student, view contact details and contact them personaly.
			5. Receive notifications about responses
		- Exceptional Flow:
			1. Login to application
			2. Error when loging in, contact IT team to hopefuly fix the problem.
-  Student Book-Session Story:
	-	Basic Flow:
		1. Login to application
		2. Select “Book a Session”
		4. Find a session you like and choose “Book Session”
		5. Receive notifications about session amendments.
	- Alternative Flow 1:
		1. Login to application.
		2. Select "My Sessions" to see what is your availability.
		3. Select "Book a Session"
		4. Filter by date
		5. Find a session of interest and "Book Session".
	- Alternative Flow 2:
		1. Login to application.
		2. Select "My Sessions" to see what is your availability.
		3. Select "Book a Session"
		4. Receive an error message : "You need to give feedback to your previously completed session before booking a new one."
		5. Go back to menu and rate the previous sessions.
	- Exceptional Flow:
		1. Login to application
		2. Select "My Sessions" to see what is your availability.
		3. The connection with the server is lost.
		4. Receive an error message.
-    Student Give-Feedback Story:
	    - Basic Flow:
		    1.  Login to application
		    2. Select “My Sessions”
		    3. Select completed session and choose “Give Feedback”
		    4. Give 5 stars because you really enjoyed the session.
		- Alternative Flow:
			1. Login to application
			2.	Select "My Sessions"
			3.	Select completed session and choose "Give Feedback".
			4.	Go to the session details again to rememeber correctly about the session.
			5.  Select "return" and go back to the feedback form.
		- Exceptional Flow:
			1. Login to application
			2. Error when loging in, contact IT team to hopefuly fix the problem.
	

**Student Book Session Story** atomic requirements:
Functional:
-   REQ1: Students **must** be able to make an account and login
    
-   REQ2: Students **must** be able to view a list of sessions available to them
    
-   REQ3: Students **should** be able to filter these sessions by preference (e.g. topic, date, etc) so that they can more easily find a session they would like to attend.
    
-   REQ4: Students **must** be able to book sessions so that the doctor hosting the session knows they are attending and therefore the number of students to expect.
    
-   REQ5: Students **must** be able to receive notifications about changes to sessions so that they can be aware of and plan around changes or cancellations.
    
-   REQ6: Students **must** be able to ‘un-book’ a session they have previously booked so that the doctor hosting the session is aware they are no longer attending, and so other students can potentially attend instead if there are limited numbers.
    
-   REQ7: Students **should** not be able to book a session which clashes with a session they have previously booked, as it would be impossible for them to attend both.

-   REQ8: Students **should** not be able to book another session without completing the feedback for the previous session they attended.

Non-Functional:
-   REQ9: Notifications about changes to sessions **should** happen within minutes of the doctor making these changes so students can always be up to date
    
-   REQ10: The system **should** be intuitive and easy to use, as it will be used by a large demographic of different ages and technical ability.
    
-   REQ11: The system **should** allow students to accomplish tasks such as booking/unbooking sessions as quickly as possible.


		

 

