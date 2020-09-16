How to run the server : 

-You can open this project in your IDE (in IntelliJ, open the "pom.xml" file, and chose to open it as a project) or simply use command line.

-Download Maven

-Run ```mvn package``` to run the tests and package the application into a single JAR file.

-Use ```java -jar filename.jar``` to run the server.

-When running, the application should start a local server (usually available at localhost:8080, check the logs to know the port).

-Open the URL in your browser, you should see a welcome message

-The application provides an API to be used with the frontend (see ../vue_frontend)