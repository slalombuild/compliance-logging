import org.gradle.api.JavaVersion

object Version {
    // @formatter:off
    // Project
    val java                                = JavaVersion.VERSION_1_8.toString()
    const val project                       = "1.0.0"

    // Dependencies
    const val assertj                       = "3.15.0"
    const val guava                         = "29.0-jre"
    const val jackson                       = "2.10.3"
    const val junit                         = "5.6.2"
    const val log4j2                        = "2.13.1"
    const val logback                       = "1.2.3"
    const val logstash                      = "7.2"
    const val spring                        = "2.7.0"
    const val slf4j                         = "1.7.30"

    // Gradle plugins
    const val checkstyle_gradle_plugin      = "8.31"
    const val google_format_gradle_plugin   = "0.8"
    const val lombok_gradle_plugin          = "5.0.0"
    // @formatter:on
}

object Developer {
    private val slalom = Organization(name = "Slalom LLC", url = "https://www.slalombuild.com")
    val carl = DeveloperInternal(id = "carl", name = "Carl-Philipp Harmant", email = "carlphilipp.harmant@slalom.com", organization = slalom)
    val antonio = DeveloperInternal(id = "antonio", name = "Antonio Castillo", email = "antonio.castillo@slalom.com", organization = slalom)
    val rishi = DeveloperInternal(id = "rishi", name = "Rishi Singh", email = "rishis@slalom.com", organization = slalom)
}

object License {
    val MIT = LicenseInternal(name = "MIT License", url = "https://opensource.org/licenses/MIT")
}

object GitHub {
    const val connection = "scm:git:git://github.com:SlalomBuild/compliance-logging.git"
    const val developerConnection = "scm:git:https://github.com/SlalomBuild/compliance-logging.git"
    const val url = "https://github.com/SlalomBuild/compliance-logging"
}

data class Organization(val name: String, val url: String)
data class DeveloperInternal(val id: String, val name: String, val email: String, val organization: Organization)
data class LicenseInternal(val name: String, val url: String)