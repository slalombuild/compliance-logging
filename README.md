<p align="center"><img src="docs/images/slalom.png" align="center" width="300" hspace="10" vspace="10"></p>
<h6 align="center">presents</h6>
<h3 align="center"><b>Compliance Logging</b></h3>
<h4 align="center">A library that allows developers to mask sensitive information when logging.</h4>

<p align="center">
<a href="LICENSE" alt="Project License"><img src="https://img.shields.io/apm/l/vim-mode?style=for-the-badge"></a>
</p>

### Description

This library has been built to help developers masking PII, PCI and PHI when logging with your favorite framework.<br />
For instance, if you want to log a JSON String that contains some sensitive information, you would obtain something like:

```log
12:44:09 [main] INFO  MyClass - {"login":"john","password":"***********"}
```

The library supports right now two types of objects that can be masked: JSON and Lombok.<br />
To be able to achieve that and keep your logging framework still performant, the developer needs to indicate the framework with a mask. <br />
For example:
```log
log.info("{}", "{\"login\":\"john\",\"password":\"mypassword\"});
```
Will not mask anything, while with our mask:
```log
log.info(MaskType.JSON, "{}", "{\"login\":\"john\",\"password":\"mypassword\"});
```
It will mask the password (or any other fields listed as sensitive).

We provide different [examples](examples) that you can checkout in our repository.

#### Usage with Log4j2
Import the latest version of the library in your project:<br />
Maven:
```xml
<dependency>
    <groupId>com.slalom</groupId>
    <artifactId>compliance-logging-log4j2</artifactId>
    <version>{LATEST_VERSION}</version>
</dependency>
```
Gradle (Kotlin DSL):
```kotlin
dependencies {
    implementation(group = "com.slalom", name = "compliance-logging-log4j2", version = "{LATEST_VERSION}")
}
```
Then three things needs to be setup in your Log4j2 configuration:
1. Package scanning (to scan our plugin).
```xml
<Configuration tatus="WARN" packages="com.slalom.logging">
  ...
</Configuration>
```

2. Add a property that represents the list of fields that need to be masked, the separator being a coma.

```xml
<Properties>
    <Property name="COMPLIANCE_FIELDS">password,ssn</Property>
</Properties>
```
3. Usage of our plugin pattern (%mm or %maskMessage)
```xml
<PatternLayout pattern="%d{HH:mm:ss} [%t] %-5level %logger{36} - %mm{${COMPLIANCE_FIELDS}}%n"/>
```
Full example:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration tatus="WARN" packages="com.slalom.logging">
    <Properties>
        <Property name="COMPLIANCE_FIELDS">password,ssn</Property>
        <Property name="CONSOLE_LOG_PATTERN">%d{HH:mm:ss} [%t] %-5level %logger{36} - %mm{${COMPLIANCE_FIELDS}}%n
        </Property>
    </Properties>
    <appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="${sys:CONSOLE_LOG_PATTERN}"/>
        </Console>
    </appenders>
    <loggers>
        <root level="info">
            <appender-ref ref="Console"/>
        </root>
    </loggers>
</Configuration>
```
[Simple example](examples/simple-log4j2-example) - [Spring Boot example](examples/spring-boot-log4j2-example)

#### Usage with Logback
Import the latest version of the library in your project:<br />
Maven:
```xml
<dependency>
    <groupId>com.slalom</groupId>
    <artifactId>compliance-logging-logback</artifactId>
    <version>{LATEST_VERSION}</version>
</dependency>
```
Gradle (Kotlin DSL):
```kotlin
dependencies {
    implementation(group = "com.slalom", name = "compliance-logging-logback", version = "{LATEST_VERSION}")
}
```
Then your logback configuration needs to be setup with our layout. Here is a full example:
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.core.encoder.LayoutWrappingEncoder">
            <layout class="com.slalom.logging.compliance.logback.PatternMaskingLayout">
                <fields>password,ssn</fields>
                <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
            </layout>
        </encoder>
    </appender>

    <root level="info">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```
[Simple example](examples/simple-logback-example) - [Spring Boot example](examples/spring-boot-logback-example)

#### Questions?
[Create an issue](issues/new)

## Development

[Developer notes](DEV.md)

#### License
```text
MIT License

Copyright (c) 2020 Slalom LLC

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```