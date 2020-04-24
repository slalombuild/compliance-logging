plugins {
    id("io.freefair.lombok") version "5.0.0"
}

description = "Compliance Logback library"

dependencies {
    api(project(":compliance-logging-common"))
    implementation(group = "ch.qos.logback", name = "logback-core", version = "1.2.3")
    implementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter")
}