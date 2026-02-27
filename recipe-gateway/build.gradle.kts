plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "com.ankit007.recipe"
version = "unspecified"


val springCloudVersion: String by project
val springDocVersion: String by project

repositories {
    mavenCentral()
}

dependencies {

    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"))
    implementation(platform("org.springdoc:springdoc-openapi-bom:${springDocVersion}"))


    //Spring Starters
//    implementation("org.springframework.boot:spring-cloud-starter-gateway")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway-server-webflux")

    // Spring-doc OpenAPI docs
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


}

tasks.test {
    useJUnitPlatform()
}