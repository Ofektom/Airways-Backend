# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy the packaged jar file into the container
COPY target/*.jar /app/app.jar

# Expose the port specified by the PORT environment variable
ENV PORT 8082
EXPOSE 8082

# Ensure the application uses the correct port and listens on all interfaces
ENV SERVER_PORT ${PORT}

# Start the Spring Boot application
CMD ["java", "-jar", "-Dserver.port=${SERVER_PORT}", "/app/app.jar"]
