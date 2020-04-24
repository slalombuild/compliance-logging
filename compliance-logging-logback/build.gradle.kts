plugins {
    id("io.freefair.lombok") version Version.lombok_gradle_plugin
}

description = "Compliance Logback library"

dependencies {
    api(project(":compliance-logging-common"))
    implementation(group = "ch.qos.logback", name = "logback-core", version = Version.logback)
    implementation(group = "ch.qos.logback", name = "logback-classic", version = Version.logback)

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter")
}