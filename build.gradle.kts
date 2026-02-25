plugins {
	java
	id("org.springframework.boot") version "4.0.1" apply false
	id("io.spring.dependency-management") version "1.1.7" apply false
	id("com.google.protobuf") version "0.9.6" apply false
	id("com.netflix.dgs.codegen") version "8.1.1" apply false
}


subprojects {
	apply(plugin = "java")

	group = "com.recipe"
	version = "0.0.1-SNAPSHOT"

	java {
		toolchain {
			languageVersion.set(JavaLanguageVersion.of(25))
		}
	}

	repositories {
		mavenCentral()
	}

	dependencies {

		implementation("org.springframework.boot:spring-boot-starter")

		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")

		testCompileOnly("org.projectlombok:lombok")
		testAnnotationProcessor("org.projectlombok:lombok")

		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	}
}