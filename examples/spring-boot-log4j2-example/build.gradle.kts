plugins {
    id("io.freefair.lombok") version Version.lombok_gradle_plugin
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:${Version.spring}"))
    implementation(platform("org.apache.logging.log4j:log4j-bom:${Version.log4j2}"))

    implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-log4j2")
    implementation(project(":compliance-logging-log4j2"))
}

// Do not publish artifact
project.tasks.publishMavenPublicationToMavenLocal.get().enabled = false

configurations {
    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
}