# Use a base image for Java
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file
COPY target/Inventoryservice.jar .

# Expose the application port
EXPOSE 8082

# Run the application
CMD ["java", "-jar"]
