# Stage 1: Build
FROM maven:3.9.2-eclipse-temurin-17 AS build

WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY src ./src

# Build the JAR
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/attendance-app-0.0.1-SNAPSHOT.jar app.jar

# Expose the port Render will use
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","app.jar"]
