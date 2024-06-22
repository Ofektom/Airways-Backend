# Stage 1: Build the Spring Boot application
FROM openjdk:17 AS build

# Set the working directory
WORKDIR /app

# Copy the Maven wrapper and project files into the container
COPY ./mvnw ./mvnw
COPY ./.mvn ./.mvn
COPY ./pom.xml ./pom.xml
COPY ./src ./src

# Build the Spring Boot application
RUN ./mvnw clean package -DskipTests

# Stage 2: Set up Nginx and the Spring Boot application
FROM nginx:stable-alpine AS production

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage to the production stage
COPY --from=build /app/target/*.jar /app/app.jar

# Copy the Nginx configuration file to the container
COPY ./nginx.conf /etc/nginx/nginx.conf

# Expose ports (80 for Nginx, 8082 for Spring Boot)
EXPOSE 80 8082

# Start the Spring Boot application and Nginx
CMD ["sh", "-c", "java -jar /app/app.jar & nginx -g 'daemon off;'"]