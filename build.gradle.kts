import com.github.sherter.googlejavaformatgradleplugin.GoogleJavaFormat
import io.freefair.gradle.plugins.lombok.tasks.GenerateLombokConfig
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    `maven-publish`
    checkstyle
    id("com.github.sherter.google-java-format") version Version.google_format_gradle_plugin
    id("io.freefair.lombok") version Version.lombok_gradle_plugin
}

subprojects {
    group = "com.slalom"
    version = Version.project

    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.sherter.google-java-format")
    apply(plugin = "checkstyle")
    apply(plugin = "io.freefair.lombok")

    repositories {
        mavenCentral()
    }

    dependencies {
        "testImplementation"(platform("org.junit:junit-bom:${Version.junit}"))
        "testImplementation"(group = "org.assertj", name = "assertj-core", version = Version.assertj)
    }

    checkstyle {
        version = Version.checkstyle_gradle_plugin.toDouble()
        isIgnoreFailures = false
        maxErrors = 0
        maxWarnings = 0
    }

    lombok {
        setVersion(Version.lombok)
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                from(components["java"])
                pom {
                    name.set(project.name)
                    description.set(project.description)
                    url.set("https://github.com/carlphilipp/compliance-logging")
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    developers {
                        developer {
                            id.set("carl")
                            name.set("Carl-Philipp Harmant")
                            email.set("carlphilipp.harmant@slalom.com")
                            organization.set("Slalom LLC")
                            organizationUrl.set("https://www.slalombuild.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:git@github.com:carlphilipp/compliance-logging.git")
                        developerConnection.set("scm:git:git@github.com:carlphilipp/compliance-logging.git")
                        url.set("https://github.com/carlphilipp/compliance-logging")
                    }
                }
            }
        }
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = Version.java
        targetCompatibility = Version.java
        options.compilerArgs.add("-Xlint:unchecked")
        options.isDeprecation = true
    }

    tasks.withType<Test> {
        useJUnitPlatform()

        testLogging {
            events = mutableSetOf(PASSED, FAILED, SKIPPED)
            exceptionFormat = FULL
        }
    }

    tasks.withType<Checkstyle> {
        val googleJavaFormatTask = tasks.withType<GoogleJavaFormat>()
        dependsOn(googleJavaFormatTask)
    }

    tasks.withType<GenerateLombokConfig> {
        enabled = false
    }
}