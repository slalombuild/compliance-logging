plugins {
    `java`
    id("org.springframework.boot") version "2.2.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

dependencies {
    implementation(project(":compliance-logging-log4j2"))
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-log4j2")

    compileOnly(group = "org.projectlombok", name = "lombok", version = "1.18.6")

    annotationProcessor(group = "org.projectlombok", name = "lombok", version = "1.18.6")
}

configurations {
    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
}