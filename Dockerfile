# Base image
FROM eclipse-temurin:17-jdk-alpine

# Working directory
WORKDIR /app

# Copy jar file
COPY target/deliveryapp-0.0.1-SNAPSHOT.jar app.jar

# Start application
ENTRYPOINT ["java", "-jar", "app.jar"] 