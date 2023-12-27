# Use a base image with Java installed
FROM openjdk:17
LABEL maintainer="kush parsaniya"

# Create directories inside the container
RUN mkdir -p /app /postgresql

# Add the Spring Boot JAR from the local build context to the container
ADD target/backend-0.0.1-SNAPSHOT.jar /app/easybank-docker.jar

# Download the PostgreSQL JDBC driver directly into the container
RUN curl -o /postgresql/postgresql.jar https://jdbc.postgresql.org/download/postgresql-8.jar

# Set the working directory
WORKDIR /app

# Set the classpath to include the PostgreSQL JDBC driver and your Spring Boot application
ENV CLASSPATH /postgresql/postgresql.jar:/app/easybank-docker.jar

# Set the entry point to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "easybank-docker.jar"]
