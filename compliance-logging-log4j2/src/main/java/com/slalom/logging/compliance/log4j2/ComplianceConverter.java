package com.slalom.logging.compliance.log4j2;

import com.slalom.logging.compliance.common.MaskService;
import com.slalom.logging.compliance.common.MaskType;
import com.slalom.logging.compliance.common.impl.JsonMaskService;
import com.slalom.logging.compliance.common.impl.LombokMaskService;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.status.StatusLogger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Plugin(name = "ComplianceConverter", category = "Converter")
@ConverterKeys({"mm", "maskMessage"})
public class ComplianceConverter extends LogEventPatternConverter {

    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final String NAME = "mm";

    @Getter(AccessLevel.PACKAGE)
    private boolean enabled = true;
    private final MaskService jsonMaskService;
    private final MaskService lombokMaskService;

    public static ComplianceConverter newInstance(final String[] options) {
        return new ComplianceConverter(options);
    }

    private ComplianceConverter(final String[] options) {
        super(NAME, NAME);
        final List<String> fields = extractFields(options);
        if (fields.isEmpty()) {
            LOGGER.warn("Compliance masking disabled, no option provided");
            enabled = false;
        } else if (fields.get(0).startsWith("${")) {
            LOGGER.warn("Compliance masking disabled, the provided option '{}' is not valid", fields.get(0));
            enabled = false;
            fields.clear();
        }
        jsonMaskService = new JsonMaskService(fields);
        lombokMaskService = new LombokMaskService(fields);
    }

    private List<String> extractFields(final String[] options) {
        return Stream.of(Optional.ofNullable(options.length == 0 ? "" : options[0]).orElse("").trim().split(","))
                .map(String::trim)
                .filter(field -> !field.isEmpty() && !field.trim().equals(""))
                .collect(Collectors.toList());
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