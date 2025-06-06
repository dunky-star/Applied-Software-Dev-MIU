# -------- Stage 1: Build --------
FROM maven:3.9.4-eclipse-temurin-17 AS builder
# Set build context
WORKDIR /app

# Copy project files
COPY . .

# Build application, skip tests for faster CI builds (optional toggle)
RUN mvn clean package -DskipTests

# -------- Stage 2: Run --------
FROM amazoncorretto:17-alpine

# Metadata
LABEL maintainer="dunkygeoffrey39@gmail.com"
LABEL version="1.0.1"
LABEL description="Production-grade Docker image for Spring Boot Hotel Booking Application"

# Create application directory
WORKDIR /app

# Copy only the packaged JAR from builder stage
COPY --from=builder /app/target/*.jar hotel-app.jar

# Expose app port (update to match your Spring Boot `server.port`)
EXPOSE 8081

# Use JVM best practices for containerized workloads (e.g., memory limits)
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=80.0", "-jar", "hotel-app.jar"]