# ---------- Build Stage ----------
FROM eclipse-temurin:18-alpine AS build
WORKDIR /app

# Install Maven
RUN apk add --no-cache maven bash

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the project
RUN mvn -B -DskipTests clean package

# ---------- Runtime Stage ----------
FROM eclipse-temurin:18-jre
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
