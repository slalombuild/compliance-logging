dependencies {
    implementation(project(":compliance-logging-logback"))
    implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = Version.jackson)
}

// Do not publish artifact
project.tasks.publishMavenPublicationToMavenLocal.get().enabled = false