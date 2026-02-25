plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "com.ankit007"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-flyway")
}

tasks.test {
    useJUnitPlatform()
}

tasks.bootJar {
    enabled = false
}