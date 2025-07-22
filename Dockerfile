# Stage 1: Dependencias
FROM maven:3.9.11-eclipse-temurin-21 AS dependencies
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Stage 2: Build
FROM maven:3.9.11-eclipse-temurin-21 AS builder
WORKDIR /app
COPY --from=dependencies /root/.m2 /root/.m2
COPY . .
RUN mvn clean package -DskipTests

# Stage 3: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080

