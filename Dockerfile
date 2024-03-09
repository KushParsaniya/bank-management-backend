# Use a base image with Java installed
FROM openjdk:17
LABEL maintainer="kush parsaniya"

# Create a directory inside the container
RUN mkdir /app

# Add the Spring Boot JAR from the local build context to the container
COPY target/backend-0.0.1-SNAPSHOT.jar /app/easybank-docker.jar

# Set the working directory
WORKDIR /app

# Set the entry point to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "easybank-docker.jar"]

# Expose the port the application runs on
EXPOSE 8080
