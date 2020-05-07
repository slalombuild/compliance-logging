description = "Compliance Log4j2 library"

dependencies {
    api(platform("org.apache.logging.log4j:log4j-bom:${Version.log4j2}"))
    api(project(":compliance-logging-core"))
    api(group = "org.apache.logging.log4j", name = "log4j-api")
    api(group = "org.apache.logging.log4j", name = "log4j-core")

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter")
}