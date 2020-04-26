import org.gradle.api.plugins.ExtraPropertiesExtension
import java.io.File
import java.util.Properties

fun loadPrivateKey(extra: ExtraPropertiesExtension) {
    val signaturePropertyFile = File("config/signature/signature.properties")
    if (signaturePropertyFile.exists()) {
        val signatureProps = Properties()
        val signatureFile = signaturePropertyFile.inputStream()
        signatureFile.use { signatureProps.load(signatureFile) }

        val secretKeyFile = signatureProps.getProperty("signing.secretKeyFile") ?: throw RuntimeException("signing.secretKeyFile property is mandatory")
        if (File(secretKeyFile).exists()) {
            val secretKey = File(secretKeyFile).inputStream().use {
                it.readBytes().toString(Charsets.UTF_8)
            }
            val password = signatureProps.getProperty("signing.password") ?: throw RuntimeException("signing.password property is mandatory")
            extra.set("signing.secretKey", secretKey)
            extra.set("signing.password", password)
        } else {
            throw RuntimeException("$secretKeyFile does not exist.")
        }
    } else {
        throw RuntimeException("config/signature/signature.properties does not exist.")
    }
}