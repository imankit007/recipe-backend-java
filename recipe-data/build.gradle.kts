plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

val jakartaVersion: String by project

dependencies {
    implementation(project(":recipe-common"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("jakarta.validation:jakarta.validation-api:${jakartaVersion}")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}