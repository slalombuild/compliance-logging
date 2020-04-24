package com.slalom.logging.compliance.common.impl;

import com.slalom.logging.compliance.common.MaskService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class AbstractService implements MaskService {

    protected static final String DEFAULT_MASK = "***********";

    protected final String fieldRegex;

    protected AbstractService(final List<String> fields) {
        fieldRegex = String.join("|", fields);
    }

    protected String maskMessage(final String message, final Pattern pattern, final String replacementRegex) {
        try {
            final StringBuffer buffer = new StringBuffer();
            final Matcher matcher = pattern.matcher(message);
            while (matcher.find()) {
                matcher.appendReplacement(buffer, replacementRegex);
            }
            matcher.appendTail(buffer);
            return buffer.toString();
        } catch (Exception e) {
            return message;
        }
    }
}