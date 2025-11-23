# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml .
RUN mvn -q -e dependency:go-offline

COPY src ./src

RUN mvn -q -e clean package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:21-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8100

ENTRYPOINT ["java", "-jar", "app.jar"]
