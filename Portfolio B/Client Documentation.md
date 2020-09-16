# Portfolio B

## Client Documentation

### License documentation

Normally, everything was defined in the contract we signed during our first meeting : everything belongs to the Client at the end.

### Deployment Instructions

The application is already deployed and runs at the following address (user credentials will be provided by email) : http://medical-teaching-frontend.s3-website-us-east-1.amazonaws.com/dist/index.html

If you want to deploy it on your own AWS account (or the account of a developer working on the application), follow these instructions :

Need : an AWS account, a GitHub account, linking Circle Ci to the github Repo of the project

-Create an EC2 instance (default linux image, free tier is enough), associate it to an elastic IP (so that it doesn't change on restart), keep the RSA key in a secure place

-Install Java 8 on this instance : ```sudo yum install java-1.8.0```

-Create the following directory : ```~/backend```

-Create an S3 bucket, configure it for static website hosting, and modify the URL in .circleci/config.yml for the "deploy-vue" job so that it match the URL of your S3 bucket

-Create an IAM role with full S3 access

-Create the following environment variables for your project in CircleCI :
* EC2IP : the elastic IP of the EC2 instance
* AWS_ACCESS_KEY_ID : the access key of the IAM role you configured
* AWS_SECRET_ACCESS_KEY : the secret access key of the IAM role you configured

-Configure an SSH key in CircleCI with the RSA key for your EC2 instance

-Replace the SSH fingerprint in .circleci/config.yml (in "deploy-pring" job) by the one configured in previous step

The application will be deployed each time a commit is done to the branch "continuous-deployment" of the GitHub Repo.

### How to use the web app

- Navigate to the URL where the web app is deployed
- You will be presented with a **login** screen: here enter the credentials you have been provided
- You will first be greeted with a view of the **upcoming teaching** sessions. Clicking on one of these sessions shows additional details about it, and users can click to book this session or to mark it as completed. A list of users currently booked on is also shown underneath these options.
- At the bottom of the screen is a menu bar, allowing the user to change the page they are currently viewing. The options for this are:
   * **Next Sessions**, as described above. This section also allows a user to create a new session, by clicking on the '+' icon. The user can set a title, location, description, time, and a doctor to host the session, if applicable.
   * **Reviews**, which shows a list of sessions which have been marked as completed, allowing those who have attended these sessions to leave feedback. Clicking on one of these sessions shows a list of users who were booked onto it and all the feedback that has been left for it.
   * **Opportunities**, which at this point has no functionality, but ideally would allow students to look for research opportunities if the project was to be improved further.
   * **Sign up**, which allows the user to add a new doctor to the database, who can then be set as the host when creating a new session. An email address can be entered, which is shown on a session's page, allowing for students to contact the doctor for more information on a session.