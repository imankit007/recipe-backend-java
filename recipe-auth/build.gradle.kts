plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencyManagement {
    imports {
        mavenBom(
            "org.springframework.grpc:spring-grpc-dependencies:${property("springGrpcVersion")}"
        )
    }
}

dependencies {

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

    // Guava
    implementation("com.google.guava:guava:33.5.0-jre")

    // Validation API
    implementation("jakarta.validation:jakarta.validation-api:3.1.0")

    // Migrations
    implementation("org.flywaydb:flyway-database-postgresql")

    // JWT dependencies
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
}

tasks.test {
    useJUnitPlatform()
}