# Use an official OpenJDK runtime as the base image
FROM openjdk:17

# Set the working directory
WORKDIR /app

# Copy the JAR file to the container
COPY target/*.jar /app/app.jar

# Install Nginx
RUN apt-get update && apt-get install -y nginx

# Copy custom Nginx configuration file
COPY nginx-backend.conf /etc/nginx/conf.d/default.conf

# Expose the port specified by Render or default to 80
ENV PORT 80
EXPOSE 80

# Start Nginx and the Spring Boot application
CMD ["sh", "-c", "nginx -g 'daemon off;' & java -Dserver.port=8082 -jar /app/app.jar"]
