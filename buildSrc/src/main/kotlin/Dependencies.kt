import org.gradle.api.JavaVersion

object Version {
    // Project
    val java                                = JavaVersion.VERSION_1_8.toString()
    const val project                       = "0.0.1-SNAPSHOT"

    // Dependencies
    const val assertj                       = "3.15.0"
    const val guava                         = "29.0-jre"
    const val jackson                       = "2.10.3"
    const val junit                         = "5.6.2"
    const val log4j2                        = "2.13.1"
    const val logback                       = "1.2.3"
    const val spring                        = "2.2.6.RELEASE"
    const val slf4j                         = "1.7.30"

    // Gradle plugins
    const val checkstyle_gradle_plugin      = "8.31"
    const val google_format_gradle_plugin   = "0.8"
    const val lombok_gradle_plugin          = "5.0.0"
}

object Developer {
    private val slalom = Organization(name = "Slalom LLC", url="https://www.slalombuild.com")
    val carl = DeveloperInternal(id = "carl",name = "Carl-Philipp Harmant", email = "carlphilipp.harmant@slalom.com", organization = slalom)
}

object License {
    val MIT = LicenseInternal(name = "MIT License", url = "https://opensource.org/licenses/MIT")
}

object GitHub {
    const val connection = "scm:git:git@github.com:carlphilipp/compliance-logging.git"
    const val developerConnection = "scm:git:git@github.com:carlphilipp/compliance-logging.git"
    const val url = "https://github.com/carlphilipp/compliance-logging"
}

data class Organization(val name: String, val url : String)
data class DeveloperInternal(val id: String, val name: String, val email : String, val organization: Organization)
data class LicenseInternal(val name: String, val url: String)