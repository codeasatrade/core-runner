# Description: A Dockerfile for Java projects
# Stage 1: Download dependencies and plugins
FROM maven:3.9-sapmachine-17 AS dependencies

# Set a working directory for Java projects
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY global-pom.xml pom.xml

# Resolve dependencies and plugins (without compiling the code)
RUN mvn dependency:resolve-plugins dependency:go-offline -B

# Stage 2: Copy source code and cached dependencies
FROM maven:3.9-sapmachine-17 AS builder

WORKDIR /app

# Copy POM files and cached dependencies from the previous stage
COPY --from=dependencies /root/.m2 /root/.m2

# copy the source code
COPY java-projects /app/

# Copy an entrypoint script (see step 2)
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Set the entrypoint to handle compilation/execution
ENTRYPOINT ["/entrypoint.sh"]

# Expose the port your application runs on
EXPOSE 49000