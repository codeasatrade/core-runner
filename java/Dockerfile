# Use OpenJDK with Maven and Gradle preinstalled
FROM maven:3.8.6-openjdk-17-slim AS builder

# Set a working directory for Java projects
WORKDIR /app

# Install Gradle (optional)
RUN apt-get update && apt-get install -y gradle

# Copy an entrypoint script (see step 2)
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Set the entrypoint to handle compilation/execution
ENTRYPOINT ["/entrypoint.sh"]