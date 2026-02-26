plugins {
    id("com.google.protobuf")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}


val springGrpcVersion: String by project

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {

    implementation(platform("org.springframework.grpc:spring-grpc-dependencies:${springGrpcVersion}"))

    implementation("io.grpc:grpc-services")
    implementation("io.grpc:grpc-protobuf")
    implementation("io.grpc:grpc-stub")
    implementation("io.grpc:grpc-netty-shaded")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.33.5"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.79.0"
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("grpc") {
//                    option("@generated=omit")
                }
            }
        }
    }
}

tasks.register<Exec>("bufLint", ) {
    group = "build"
    description = "Generates lint checks"

    commandLine("buf", "lint")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

tasks.test {
    useJUnitPlatform()
}