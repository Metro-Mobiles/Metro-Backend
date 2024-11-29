# Use the official Maven image to compile and build your application
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the project's pom.xml and source files to the container
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package

# Use the official OpenJDK image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your application runs on (e.g., 8080)
EXPOSE 8090

# Define the command to run your application
CMD ["java", "-jar", "app.jar"]
