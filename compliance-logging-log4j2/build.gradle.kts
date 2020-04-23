plugins {
    kotlin("jvm")
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("maven-publish")
}

dependencyManagement {
    imports {
        mavenBom("org.apache.logging.log4j:log4j-bom:2.13.1")
    }
}
dependencies {
    api(project(":compliance-logging-common"))

    implementation(group = "org.jetbrains.kotlin", name = "kotlin-stdlib-jdk8")
    implementation(group = "org.apache.logging.log4j", name = "log4j-api")
    implementation(group = "org.apache.logging.log4j", name = "log4j-core")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.slalom"
            artifactId = "compliance-logging-log4j2"
            version = "0.0.1-SNAPSHOT"

            from(components["java"])
        }
    }
}
