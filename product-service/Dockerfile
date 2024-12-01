# Use a base image for Java
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file
COPY pom.xml .
COPY src ./src

# Expose the application port
EXPOSE 8084

