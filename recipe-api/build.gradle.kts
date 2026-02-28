plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "com.ankit007.recipe"
version = "unspecified"

repositories {
    mavenCentral()
}

val springDocVersion: String by project
val guavaVersion: String by project

val springGrpcVersion: String by project

dependencies {

    implementation(platform("org.springframework.grpc:spring-grpc-dependencies:${springGrpcVersion}"))


    implementation(project(":recipe-common"))
    implementation(project(":recipe-data"))
    implementation(project(":recipe-migration"))
    implementation(project(":recipe-grpc-proto"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${springDocVersion}")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

//    implementation("org.springframework.grpc:spring-grpc-spring-boot-starter")

    implementation("net.devh:grpc-client-spring-boot-starter:3.1.0.RELEASE")

    implementation("io.grpc:grpc-netty-shaded")

    // Database driver
    implementation("org.postgresql:postgresql")

    // Migrations
    implementation("org.flywaydb:flyway-database-postgresql")

    // Guava
    implementation("com.google.guava:guava:${guavaVersion}")

}

tasks.test {
    useJUnitPlatform()
}