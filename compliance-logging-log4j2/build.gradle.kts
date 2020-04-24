plugins {
    id("io.freefair.lombok") version Version.lombok_gradle_plugin
}

description = "Compliance Log4j2 library"

dependencies {
    implementation(platform("org.apache.logging.log4j:log4j-bom:${Version.log4j2}"))
    api(project(":compliance-logging-common"))

    implementation(group = "org.apache.logging.log4j", name = "log4j-api")
    implementation(group = "org.apache.logging.log4j", name = "log4j-core")

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter")
}