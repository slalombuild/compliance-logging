dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:${Version.spring}"))
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
    implementation(project(":compliance-logging-logback-logstash"))
}

// Do not publish artifact
project.afterEvaluate {
    project.tasks.getByName("publishMavenPublicationToMavenLocal").enabled = false
}