FROM eclipse-temurin:21-alpine
COPY target/demo-0.0.1-SNAPSHOT.jar .
EXPOSE 8100
ENTRYPOINT ["java", "-jar", "/demo-0.0.1-SNAPSHOT.jar"]