package com.slalom.logging.compliance.log4j2;

import com.slalom.logging.compliance.common.JsonMaskService;
import com.slalom.logging.compliance.common.LombokMaskService;
import com.slalom.logging.compliance.common.MaskService;
import com.slalom.logging.compliance.common.MaskType;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.status.StatusLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Plugin(name = "ComplianceConverter", category = "Converter")
@ConverterKeys({"mm", "maskMessage"})
public class ComplianceConverter extends LogEventPatternConverter {

    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final String NAME = "mm";

    private boolean enabled = true;
    private final MaskService jsonMaskService;
    private final MaskService lombokMaskService;

    protected ComplianceConverter(final String[] options) {
        super(NAME, NAME);
        final List<String> fields = extractFields(options);
        if (fields.isEmpty()) {
            LOGGER.warn("Compliance converter disabled");
            enabled = false;
        }
        jsonMaskService = new JsonMaskService(fields);
        lombokMaskService = new LombokMaskService(fields);
    }

    @Override
    public void format(LogEvent event, StringBuilder outputMessage) {
        final String message = event.getMessage().getFormattedMessage();
        if (enabled) {
            final Marker marker = event.getMarker();
            outputMessage.append(maskMessage(message, marker));
        } else {
            outputMessage.append(message);
        }
    }

    private List<String> extractFields(final String[] options) {
        return Optional.ofNullable(options)
                .filter(strings -> strings.length > 0)
                .map(strings -> strings[0])
                .map(String::trim)
                .map(s -> Arrays.asList(s.split(",")))
                .orElse(new ArrayList<>());
    }

    private String maskMessage(final String message, final Marker marker) {
        if (marker != null) {
            if (MaskType.JSON_MARKER_NAME.equals(marker.getName())) {
                return jsonMaskService.maskMessage(message);
            } else if (MaskType.LOMBOK_MARKER_NAME.equals(marker.getName())) {
                return lombokMaskService.maskMessage(message);
            }
        }
        return message;
    }
}
