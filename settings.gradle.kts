rootProject.name = "compliance-logging"
include(
        ":compliance-logging-common",
        ":compliance-logging-log4j2",
        ":compliance-logging-logback",
        ":spring-boot-log4j2-example",
        ":spring-boot-logback-example"
)

project(":spring-boot-log4j2-example").projectDir = file("examples/spring-boot-log4j2-example")
project(":spring-boot-logback-example").projectDir = file("examples/spring-boot-logback-example")