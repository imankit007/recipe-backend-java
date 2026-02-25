plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

val springBootVersion: String by project
val springGrpcVersion: String by project
val lombokVersion: String by project

repositories {
    mavenCentral()
}

dependencies {

    implementation(platform("org.springframework.grpc:spring-grpc-dependencies:${springGrpcVersion}"))

    // Project Dependencies
    implementation(project(":recipe-common"))
    implementation(project(":recipe-data"))
    implementation(project(":recipe-grpc-proto"))
    implementation(project(":recipe-migration"))

    // Spring Boot starter dependencies
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.grpc:spring-grpc-spring-boot-starter")

    // gRPC dependencies
    implementation("io.grpc:grpc-protobuf")
    implementation("io.grpc:grpc-stub")
    implementation("io.grpc:grpc-netty-shaded")
    implementation("io.grpc:grpc-services")

    // Database driver
    implementation("org.postgresql:postgresql")

    // Migrations
    implementation("org.flywaydb:flyway-database-postgresql")

    // Guava
    implementation("com.google.guava:guava:33.5.0-jre")
}