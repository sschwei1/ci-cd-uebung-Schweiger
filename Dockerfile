FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B -ntp dependency:go-offline
COPY src ./src
RUN mvn -B -ntp package && ls -la target

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/target/ci-cd-uebung-1.0.0.jar /app/app.jar
ENTRYPOINT ["java", "-cp", "/app/app.jar", "com.example.cicd.App"]
