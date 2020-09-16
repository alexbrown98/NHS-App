#!/bin/bash

# Script
cd
cd backend
# Stopping the currently executing server
PID=$(cat pid.txt)
kill $PID
killall java
#Replacing the old file by the new one freshly transfered
rm backend.jar
mv backend-bis.jar backend.jar
#Launching new server in background
nohup java -jar backend.jar > log.txt 2> errors.txt < /dev/null &
#Remembering the PID to kill it latter
PID=$!
echo $PID > pid.txt
exit 0