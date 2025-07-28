FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Copy Maven wrapper and config
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Fix the permission issue before running mvnw
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy source files
COPY src ./src

# Build the project
RUN ./mvnw clean package -DskipTests

# ----------- Runtime Stage ------------
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
COPY ca.pem /app/ca.pem

EXPOSE 9728

ENTRYPOINT ["java", "-jar", "app.jar"]
