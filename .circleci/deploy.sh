#!/bin/bash

# Script
echo "Starting deploy script"

# Scp to instance
mv ~/repo/spring_application/target/demo-0.0.1-SNAPSHOT.jar ~/repo/spring_application/target/backend-bis.jar
scp -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null ~/repo/spring_application/target/backend-bis.jar ec2-user@${EC2IP}:/home/ec2-user/backend
ssh -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null ec2-user@${EC2IP} < .circleci/remote-script.sh

exit 0