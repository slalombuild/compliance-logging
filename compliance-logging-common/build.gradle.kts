plugins {
    kotlin("jvm")
    `maven-publish`
    id("io.spring.dependency-management")
}

dependencyManagement {
    imports {
        mavenBom("org.junit:junit-bom:5.6.2")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(group = "org.slf4j", name = "slf4j-api", version = "1.7.30")

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter")
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api")
    testImplementation(group = "org.assertj", name = "assertj-core", version = "3.15.0")
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.slalom"
            artifactId = "compliance-logging-common"
            version = "0.0.1-SNAPSHOT"

            from(components["java"])
        }
    }
}