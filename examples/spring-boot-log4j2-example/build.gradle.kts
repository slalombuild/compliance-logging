plugins {
    id("io.freefair.lombok") version "5.0.0"
}

project.tasks.publish.get().enabled = false

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.2.6.RELEASE"))
    implementation(platform("org.apache.logging.log4j:log4j-bom:2.13.1"))
    implementation(project(":compliance-logging-log4j2"))
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-log4j2")
}

// Do not publish artifact
project.tasks.publishMavenPublicationToMavenLocal.get().enabled = false

configurations {
    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
}