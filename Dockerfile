# Stage 1: Build
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Build the application (skip tests for speed in this stage)
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/csv-analyzer-1.0-SNAPSHOT.jar app.jar

# Define default command to show usage
ENTRYPOINT ["java", "-jar", "app.jar"]
