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
}

subprojects {
    group = "com.slalom"
    version = Version.project

    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
    }

    dependencies {
        "testImplementation"(platform("org.junit:junit-bom:${Version.junit}"))
        "testImplementation"(group = "org.assertj", name = "assertj-core", version = Version.assertj)
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
                            organization.set("Slalom Build")
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
    }

    tasks.withType<Test> {
        useJUnitPlatform()

        testLogging {
            events = mutableSetOf(PASSED, FAILED, SKIPPED)
            exceptionFormat = FULL
        }
    }
}