# Use a base image for Java
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file
COPY target/Userservice.jar .

# Expose the application port
EXPOSE 8083

# Run the application
CMD ["java", "-jar", "app.jar"]
