# FROM openjdk:17-jdk-slim AS build
# WORKDIR /app
# COPY .mvn/ .mvn/
# COPY mvnw pom.xml ./
# COPY src ./src
# RUN chmod +x mvnw && ./mvnw clean package -DskipTests
#
# FROM openjdk:17-jdk-slim
# COPY --from=build /app/target/*.jar app.jar
# COPY ca.pem /app/ca.pem
# EXPOSE 9728
# ENTRYPOINT ["java", "-jar", "app.jar"]
# ------------ Stage 1: Build the JAR ------------
FROM openjdk:17-jdk-slim AS build

# Set the working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Download dependencies (caches better if src hasn't changed)
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src ./src

# Make wrapper executable and build JAR (skip tests for speed)
RUN chmod +x mvnw && ./mvnw dependency:go-offline


# ------------ Stage 2: Create final image ------------
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy built JAR and CA cert
COPY --from=build /app/target/*.jar app.jar
COPY ca.pem /app/ca.pem

# Expose the app port
EXPOSE 9728

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
