package com.slalom.logging.compliance.common

import org.slf4j.Marker
import org.slf4j.MarkerFactory

class MaskType {
    companion object {
        const val JSON_MARKER_NAME = "JSON-COMPLIANCE"
        const val LOMBOK_MARKER_NAME = "LOMBOK-COMPLIANCE"
        val JSON: Marker = MarkerFactory.getMarker(JSON_MARKER_NAME)
        val LOMBOK: Marker = MarkerFactory.getMarker("LOMBOK-COMPLIANCE")
    }
}
