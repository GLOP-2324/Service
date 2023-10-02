# Use an official Maven image as the parent image
FROM maven:3.6-openjdk-17-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven Wrapper JAR and properties file
COPY ./.mvn/wrapper/maven-wrapper.jar ./.mvn/wrapper/maven-wrapper.properties ./.mvn/wrapper/

# Copy the project's POM file
COPY ./pom.xml .

# Download the project dependencies and plugins
RUN mvn -B dependency:resolve-plugins dependency:go-offline

# Copy the source code into the container
COPY ./src ./src

# Build the Spring Boot application
RUN mvn package

# Use an official OpenJDK runtime as the final image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from the build stage to the final image
COPY --from=build /app/target/*.jar ./app.jar

# Specify the command to run your Spring Boot application
CMD ["java", "-jar", "app.jar"]
