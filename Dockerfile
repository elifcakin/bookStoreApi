# Maven tarafından uygulamanın build edilmesi
FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

# Uygulamanın çalıştırılması
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=builder /app/target/bookStoreApi-0.0.1.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
