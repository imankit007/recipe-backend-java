plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

val springGrpcVersion: String by rootProject.extra
val jsonwebtokenVersion: String by project
dependencies {

//    implementation(project(":recipe-grpc-proto"))

    implementation(platform("org.springframework.grpc:spring-grpc-dependencies:${springGrpcVersion}"))

//    // gRPC dependencies
//    implementation("io.grpc:grpc-protobuf")
//    implementation("io.grpc:grpc-stub")
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("org.hibernate.orm:hibernate-core")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.springframework:spring-core")
    implementation("org.springframework:spring-beans")
    implementation("org.springframework.data:spring-data-commons")

    // JWT dependencies
    implementation("io.jsonwebtoken:jjwt-api:${jsonwebtokenVersion}")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:${jsonwebtokenVersion}")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:${jsonwebtokenVersion}")
}

tasks.test {
    useJUnitPlatform()
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}