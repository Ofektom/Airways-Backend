FROM openjdk:17
COPY /target/Airways-Demo-Backend-0.0.1-SNAPSHOT.jar /app/Airway-Project.jar
WORKDIR /app
EXPOSE 8082
CMD ["java", "-jar", "Airway-Project.jar"]