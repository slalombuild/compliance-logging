package com.slalom.logging.compliance.common.impl;

import java.util.List;
import java.util.regex.Pattern;

public class JsonMaskService extends AbstractService {

    private static final String JSON_REPLACEMENT_REGEX = "\"$1\":$2\"" + DEFAULT_MASK + "\"";
    private static final String JSON_PATTERN = "\"(%s)\":([   ]*)\"([^\"]+)\"";

    private final Pattern pattern;

    public JsonMaskService(final List<String> fields) {
        super(fields);
        this.pattern = Pattern.compile(String.format(JSON_PATTERN, fieldRegex));
    }

    @Override
    public String maskMessage(String message) {
        return maskMessage(message, pattern, JSON_REPLACEMENT_REGEX);
    }
}
