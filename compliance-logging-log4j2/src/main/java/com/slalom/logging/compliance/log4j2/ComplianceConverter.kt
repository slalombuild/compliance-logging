package com.slalom.logging.compliance.log4j2

import com.slalom.logging.compliance.common.JsonMaskService
import com.slalom.logging.compliance.common.LombokMaskService
import com.slalom.logging.compliance.common.MaskService
import com.slalom.logging.compliance.common.MaskType
import org.apache.logging.log4j.Marker
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.config.plugins.Plugin
import org.apache.logging.log4j.core.pattern.ConverterKeys
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter
import org.apache.logging.log4j.status.StatusLogger

@Plugin(name = "ComplianceConverter", category = "Converter")
@ConverterKeys(value = ["mm", "maskMessage"])
class ComplianceConverter(options: Array<String>?) : LogEventPatternConverter(NAME, NAME) {

    private var enabled = true
    private val jsonMaskService: MaskService
    private val lombokMaskService: MaskService

    init {
        val fields = options?.get(0)?.splitToSequence(",")?.map { field -> field.trim() }.orEmpty().toList()
        if (fields.isEmpty()) {
            LOGGER.warn("Compliance converter disabled")
            enabled = false
        }
        this.jsonMaskService = JsonMaskService(fields)
        this.lombokMaskService = LombokMaskService(fields)
    }

    override fun format(event: LogEvent, outputMessage: StringBuilder) {
        val message = event.message.formattedMessage
        if (enabled) {
            val marker: Marker? = event.marker
            outputMessage.append(maskMessage(message, marker))
        } else {
            outputMessage.append(message)
        }
    }

    private fun maskMessage(message: String, marker: Marker?): String {
        return when (marker?.name) {
            MaskType.JSON_MARKER_NAME -> jsonMaskService.maskMessage(message)
            MaskType.LOMBOK_MARKER_NAME -> lombokMaskService.maskMessage(message)
            else -> message
        }
    }

    companion object {
        private val LOGGER = StatusLogger.getLogger()
        private const val NAME = "mm"

        @JvmStatic
        fun newInstance(options: Array<String>?): ComplianceConverter {
            LOGGER.debug("Init compliance converter with options {}", options)
            return ComplianceConverter(options)
        }
    }
}