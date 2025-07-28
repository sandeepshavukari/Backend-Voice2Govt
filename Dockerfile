# Stage 1: Build the application using Maven
FROM openjdk:17-jdk-slim AS build
WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src ./src

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the built app
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV DB_HOST=""
ENV DB_PORT=""
ENV DB_NAME=""
ENV DB_USERNAME=""
ENV DB_PASSWORD=""
ENV DB_CA_CERT_PATH="/app/ca.pem"

COPY ca.pem /app/ca.pem

EXPOSE 9728
ENTRYPOINT ["java", "-jar", "app.jar"]
