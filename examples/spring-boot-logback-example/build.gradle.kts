plugins {
    id("io.freefair.lombok") version "5.0.0"
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.2.6.RELEASE"))
    implementation(platform("org.apache.logging.log4j:log4j-bom:2.13.1"))
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
    implementation(project(":compliance-logging-logback"))
}

// Do not publish artifact
project.tasks.publishMavenPublicationToMavenLocal.get().enabled = false