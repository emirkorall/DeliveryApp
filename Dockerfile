# Base image
FROM --platform=linux/amd64 eclipse-temurin:17-jdk-alpine

# Working directory
WORKDIR /app

# Copy jar file (using wildcard to find the correct jar name)
COPY target/*.jar app.jar

# Start application
ENTRYPOINT ["java", "-jar", "app.jar"] 