description = "Common compliance library containing the masking logic"

dependencies {
    implementation(group = "org.slf4j", name = "slf4j-api", version = "1.7.30")

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter")
    testImplementation(group = "com.google.guava", name = "guava", version = "29.0-jre")
    testImplementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.10.3")
    testCompileOnly(group = "org.projectlombok", name = "lombok", version = "1.18.12")
    testAnnotationProcessor(group = "org.projectlombok", name = "lombok", version = "1.18.12")
}