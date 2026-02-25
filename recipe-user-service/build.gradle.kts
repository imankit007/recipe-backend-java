plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.netflix.dgs.codegen")
}

description = "User Service for Recipe Application"

dependencies {
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter")
}

/**
 * Uses the `netflixDgsVersion` defined in the root `extra`
 */
dependencyManagement {
    imports {
        mavenBom(
                "com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:${property("netflixDgsVersion")}"
        )
    }
}

tasks.generateJava {
    schemaPaths.add("${projectDir}/src/main/resources/schema")
    packageName = "com.recipe.user.codegen"
    generateClient = true
}

tasks.bootRun {
    jvmArgs = listOf("-Duser.timezone=Asia/Kolkata")
}