package com.slalom.logging.compliance.logback;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.slalom.logging.compliance.common.MaskService;
import com.slalom.logging.compliance.common.MaskType;
import com.slalom.logging.compliance.common.impl.JsonMaskService;
import com.slalom.logging.compliance.common.impl.LombokMaskService;
import lombok.AccessLevel;
import lombok.Getter;
import org.slf4j.Marker;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PatternMaskingLayout extends PatternLayout {

    @Getter(AccessLevel.PACKAGE)
    private boolean enabled = false;
    private MaskService jsonMaskService = new JsonMaskService(Collections.emptyList());
    private MaskService lombokMaskService = new LombokMaskService(Collections.emptyList());

    public void addFields(final String fields) {
        final List<String> regex = Stream.of(fields.split(",")).map(String::trim).collect(Collectors.toList());
        enabled = true;
        jsonMaskService = new JsonMaskService(regex);
        lombokMaskService = new LombokMaskService(regex);
    }

    @Override
    public String doLayout(final ILoggingEvent event) {
        final String message = super.doLayout(event);
        if (enabled) {
            final Marker marker = event.getMarker();
            return marker == null ? message : maskMessage(message, marker);
        } else {
            return message;
        }
    }

    private String maskMessage(final String message, final Marker marker) {
        String maskedMessage = message;
        if (MaskType.JSON.getName().equals(marker.getName())) {
            maskedMessage = jsonMaskService.maskMessage(message);
        } else if (MaskType.LOMBOK.getName().equals(marker.getName())) {
            maskedMessage = lombokMaskService.maskMessage(message);
        }
        return maskedMessage;
    }
}
