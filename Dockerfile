FROM openjdk:17
VOLUME /tmp
COPY /target/*.jar /app.jar
WORKDIR /app
EXPOSE 8082
CMD ["java", "-jar", "/app.jar"]