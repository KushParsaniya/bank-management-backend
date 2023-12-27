# Use a base image with Java installed
FROM openjdk:17
LABEL maintainer="kush parsaniya"
ADD target/backend-0.0.1-SNAPSHOT.jar easybank-docker.jar

# Set the working directory in the container
# Download PostgreSQL JDBC driver (adjust the version as needed)
RUN mkdir C:\postgresql
RUN powershell -Command Invoke-WebRequest -Uri 'https://jdbc.postgresql.org/download/postgresql-{VERSION}.jar' -OutFile 'C:\postgresql\postgresql.jar'

# Set the working directory
WORKDIR /app

# Set the classpath to include the PostgreSQL JDBC driver and your Spring Boot application
ENV CLASSPATH C:\postgresql\postgresql.jar;easybank-docker.ja
ENTRYPOINT ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar.jar"]
