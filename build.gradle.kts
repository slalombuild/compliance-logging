import com.github.sherter.googlejavaformatgradleplugin.GoogleJavaFormat
import io.freefair.gradle.plugins.lombok.tasks.GenerateLombokConfig
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.STARTED

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    `java-library`
    `maven-publish`
    checkstyle
    jacoco
    signing
    id("com.github.sherter.google-java-format") version Version.google_format_gradle_plugin apply false
    id("io.freefair.lombok") version Version.lombok_gradle_plugin apply false
    id("com.dorongold.task-tree") version "1.5"
}

if (shouldSignArtifacts()) {
    loadPrivateKey(extra)
}

subprojects {
    group = "com.slalom"
    version = Version.project

    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.sherter.google-java-format")
    apply(plugin = "checkstyle")
    apply(plugin = "io.freefair.lombok")
    apply(plugin = "signing")
    apply(plugin = "jacoco")

    repositories {
        mavenCentral()
    }

    dependencies {
        "testImplementation"(platform("org.junit:junit-bom:${Version.junit}"))
        "testImplementation"(group = "org.assertj", name = "assertj-core", version = Version.assertj)
    }

    java {
        withJavadocJar()
        withSourcesJar()
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
            events = mutableSetOf(PASSED, FAILED, SKIPPED, STARTED)
            exceptionFormat = FULL
        }
    }

    tasks.named("build") {
        dependsOn(project.tasks.withType<JacocoReport>())
    }

    tasks.withType<Checkstyle> {
        dependsOn(tasks.withType<GoogleJavaFormat>())
    }

    tasks.withType<GenerateLombokConfig> {
        enabled = false
    }

    tasks.withType<JacocoReport> {
        reports {
            xml.isEnabled = false
            csv.isEnabled = false
            html.destination = file("${buildDir}/reports/jacoco")
        }
    }

    tasks.withType<Checkstyle> {
        isIgnoreFailures = false
        maxErrors = 0
        maxWarnings = 0
    }

    tasks.named("checkstyleTest") {
        enabled = false
    }

    checkstyle {
        toolVersion = Version.checkstyle_gradle_plugin
    }

    jacoco {
        toolVersion = "0.8.5"
    }

    project.afterEvaluate {
        publishing {
            publications {
                create<MavenPublication>("maven") {
                    from(components["java"])

                    groupId = project.group.toString()
                    artifactId = project.name
                    version = project.version.toString()

                    pom {
                        name.set(project.name)
                        description.set(project.description)
                        url.set(GitHub.url)
                        licenses {
                            license {
                                name.set(License.MIT.name)
                                url.set(License.MIT.url)
                            }
                        }
                        developers {
                            developer {
                                id.set(Developer.carl.id)
                                name.set(Developer.carl.name)
                                email.set(Developer.carl.email)
                                organization.set(Developer.carl.organization.name)
                                organizationUrl.set(Developer.carl.organization.url)
                            }
                            developer {
                                id.set(Developer.antonio.id)
                                name.set(Developer.antonio.name)
                                email.set(Developer.antonio.email)
                                organization.set(Developer.antonio.organization.name)
                                organizationUrl.set(Developer.antonio.organization.url)
                            }
                            developer {
                                id.set(Developer.rishi.id)
                                name.set(Developer.rishi.name)
                                email.set(Developer.rishi.email)
                                organization.set(Developer.rishi.organization.name)
                                organizationUrl.set(Developer.rishi.organization.url)
                            }
                        }
                        scm {
                            connection.set(GitHub.connection)
                            developerConnection.set(GitHub.developerConnection)
                            url.set(GitHub.url)
                        }
                    }
                }
            }
        }
        if (shouldSignArtifacts()) {
            signing {
                val password = this.project.parent!!.extra["signing.password"] as String
                val secretKey = this.project.parent!!.extra["signing.secretKey"] as String

                useInMemoryPgpKeys(secretKey, password)
                sign(publishing.publications["maven"])
            }
        }
    }
}

fun shouldSignArtifacts(): Boolean {
    return project.hasProperty("sign") && project.property("sign")!! == "true"
}