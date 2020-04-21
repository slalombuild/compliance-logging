import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED

buildscript {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java-library")

    group = "com.slalom"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_11.toString()
        targetCompatibility = JavaVersion.VERSION_11.toString()
    }

    tasks.withType<Test> {
        useJUnitPlatform()

        testLogging {
            events = mutableSetOf(PASSED, FAILED, SKIPPED)
            exceptionFormat = FULL
        }
    }
}

