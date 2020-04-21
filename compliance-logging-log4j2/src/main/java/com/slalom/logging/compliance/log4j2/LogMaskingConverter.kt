package com.slalom.logging.compliance.log4j2

import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter
import java.lang.StringBuilder

class LogMaskingConverter(): LogEventPatternConverter("","") {
    override fun format(event: LogEvent?, toAppendTo: StringBuilder?) {
        TODO("Not yet implemented")
    }

}
