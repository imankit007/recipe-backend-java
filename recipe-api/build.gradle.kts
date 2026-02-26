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

dependencies {

    implementation(project(":recipe-common"))
    implementation(project(":recipe-data"))
    implementation(project(":recipe-migration"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${springDocVersion}")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")


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