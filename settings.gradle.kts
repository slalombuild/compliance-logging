rootProject.name = "compliance-logging"
include(
        ":compliance-logging-core",
        ":compliance-logging-log4j2",
        ":compliance-logging-logback",
        ":compliance-logging-logback-logstash",
        ":spring-boot-log4j2-example",
        ":spring-boot-logback-example",
        ":simple-log4j2-example",
        ":simple-logback-example",
        ":simple-logback-logstash-example"
)

project(":spring-boot-log4j2-example").projectDir = file("examples/spring-boot-log4j2-example")
project(":spring-boot-logback-example").projectDir = file("examples/spring-boot-logback-example")
project(":simple-logback-logstash-example").projectDir = file("examples/spring-boot-logback-logstash-example")
project(":simple-log4j2-example").projectDir = file("examples/simple-log4j2-example")
project(":simple-logback-example").projectDir = file("examples/simple-logback-example")
