
# Runtime stage
FROM eclipse-temurin:25-jre

RUN apt-get update \
    && apt-get install -y --no-install-recommends curl ca-certificates \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy pre-built jar from local build output. This Dockerfile expects you to run
# the Gradle build (bootJar) before building the image. Remove the --from=builder
# reference because this Dockerfile no longer has a builder stage.
COPY build/libs/*.jar recipe-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "recipe-api.jar"]