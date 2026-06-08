FROM eclipse-temurin:25-jre

RUN apt-get update \
    && apt-get install -y --no-install-recommends curl ca-certificates \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY build/libs/*.jar recipe-gateway.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "recipe-gateway.jar"]