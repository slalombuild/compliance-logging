plugins {
    kotlin("jvm")
    `maven-publish`
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(group = "org.slf4j", name = "slf4j-api:1.7.30")
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