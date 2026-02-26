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

val guavaVersion: String by project
val jakartaVersion: String by project
val jsonwebtokenVersion: String by project

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
    implementation("com.google.guava:guava:${guavaVersion}")

    // Validation API
    implementation("jakarta.validation:jakarta.validation-api:${jakartaVersion}")

    // Migrations
    implementation("org.flywaydb:flyway-database-postgresql")

    // JWT dependencies
    implementation("io.jsonwebtoken:jjwt-api:${jsonwebtokenVersion}")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:${jsonwebtokenVersion}")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:${jsonwebtokenVersion}")
}

tasks.test {
    useJUnitPlatform()
}