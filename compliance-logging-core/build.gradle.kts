description = "Core compliance library containing the masking logic"

dependencies {
    implementation(group = "org.slf4j", name = "slf4j-api", version = Version.slf4j)

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter")
    testImplementation(group = "com.google.guava", name = "guava", version = Version.guava)
    testImplementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = Version.jackson)
}