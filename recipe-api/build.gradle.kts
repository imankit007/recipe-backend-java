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

    runtimeOnly(project(":recipe-common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.1")

}

tasks.test {
    useJUnitPlatform()
}