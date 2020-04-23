package com.slalom.logging.compliance.common

import java.util.regex.Pattern

private const val DEFAULT_MASK = "***********"

private const val JSON_REPLACEMENT_REGEX = "\"$1\":$2\"$DEFAULT_MASK\""
private const val JSON_PATTERN = "\"(%s)\":( ?)\"([^\"]+)\""

private const val LOMBOK_REPLACEMENT_REGEX = "$1=$DEFAULT_MASK$3"
private const val LOMBOK_PATTERN = "(%s)=([^\"]+?(, |\\)))"

interface MaskService {
    fun maskMessage(message: String): String
}

abstract class DefaultService(fields: List<String>) : MaskService {

    protected val fieldRegex: String = fields.joinToString("|")

    fun maskMessage(message: String, pattern: Pattern, replacementRegex: String): String {
        return try {
            val buffer = StringBuffer()
            val matcher = pattern.matcher(message)

            while (matcher.find()) {
                matcher.appendReplacement(buffer, replacementRegex)
            }
            matcher.appendTail(buffer)
            buffer.toString()
        } catch (e: Exception) {
            message
        }
    }
}

class JsonMaskService(fields: List<String>) : DefaultService(fields) {

    private val pattern: Pattern = Pattern.compile(String.format(JSON_PATTERN, fieldRegex))

    override fun maskMessage(message: String): String {
        return maskMessage(message = message, pattern = pattern, replacementRegex = JSON_REPLACEMENT_REGEX)
    }
}

class LombokMaskService(fields: List<String>) : DefaultService(fields) {

    private val pattern: Pattern = Pattern.compile(String.format(LOMBOK_PATTERN, fieldRegex))

    override fun maskMessage(message: String): String {
        return maskMessage(message = message, pattern = pattern, replacementRegex = LOMBOK_REPLACEMENT_REGEX)
    }
}
