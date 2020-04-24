plugins {
    id("io.freefair.lombok") version "5.0.0"
}

description = "Common compliance Log4j2 library containing the plugin"

dependencies {
    implementation(platform("org.apache.logging.log4j:log4j-bom:2.13.1"))
    api(project(":compliance-logging-common"))

    implementation(group = "org.apache.logging.log4j", name = "log4j-api")
    implementation(group = "org.apache.logging.log4j", name = "log4j-core")

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter")
    testImplementation(group = "org.assertj", name = "assertj-core", version = "3.15.0")
}