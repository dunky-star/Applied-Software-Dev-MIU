# Stage 1: Build the application using Maven
# --------------------------------------------
FROM maven:3.9.4-eclipse-temurin-17 as builder
# Set working directory inside builder stage
WORKDIR /app
# Copy the whole project (including pom.xml and src)
COPY . .
# Build the application and create the JAR
RUN mvn clean package -DskipTests

# --------------------------------------------

# Stage 2: Use a lightweight JRE base image for running the app
FROM amazoncorretto:17-alpine

# Set working directory inside runtime container
WORKDIR /app

# Copy only the generated JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]