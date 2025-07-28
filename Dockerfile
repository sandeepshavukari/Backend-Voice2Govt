FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src ./src
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY ca.pem /app/ca.pem
EXPOSE 9728
ENTRYPOINT ["java", "-jar", "app.jar"]
