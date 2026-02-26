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

dependencies {

    implementation(project(":recipe-common"))
    implementation(project(":recipe-data"))
    implementation(project(":recipe-migration"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.1")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")


    // Database driver
    implementation("org.postgresql:postgresql")

    // Migrations
    implementation("org.flywaydb:flyway-database-postgresql")

    // Guava
    implementation("com.google.guava:guava:33.5.0-jre")

}

tasks.test {
    useJUnitPlatform()
}