description = "Compliance Logback library"

dependencies {
    api(project(":compliance-logging-core"))
    api(group = "ch.qos.logback", name = "logback-core", version = Version.logback)
    api(group = "ch.qos.logback", name = "logback-classic", version = Version.logback)

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter")
}