description = "Compliance Logstash Logback Encoder library"

dependencies {
    api(project(":compliance-logging-logback"))
    api(group = "net.logstash.logback", name = "logstash-logback-encoder", version = Version.logstash)

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter")
}