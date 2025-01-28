#!/bin/bash

# Navigate to the mounted project directory
cd /app

# Check if it's a Maven project
if [ -f "pom.xml" ]; then
  mvn clean install
  mvn exec:java -Dexec.mainClass="${MAIN_CLASS:-com.example.Main}"
  
# Check if it's a Gradle project
elif [ -f "build.gradle" ]; then
  gradle build
  gradle run --args="${ARGS}"

# Check if it's a single Java file
elif [ -f *.java ]; then
  JAVA_FILE=$(ls *.java | head -n 1)
  CLASS_NAME="${JAVA_FILE%.*}"
  javac $JAVA_FILE
  java $CLASS_NAME

else
  echo "Error: No valid Java project detected."
  exit 1
fi