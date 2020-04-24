package com.slalom.logging.compliance.common.impl;

import java.util.List;
import java.util.regex.Pattern;

public class LombokMaskService extends AbstractService {

    private static final String LOMBOK_REPLACEMENT_REGEX = "$1=" + DEFAULT_MASK + "$3";
    private static final String LOMBOK_PATTERN = "(%s)=([^\"]+?(, |\\)))";

    private final Pattern pattern;

    public LombokMaskService(List<String> fields) {
        super(fields);
        this.pattern = Pattern.compile(String.format(LOMBOK_PATTERN, fieldRegex));
    }

    @Override
    public String maskMessage(String message) {
        return maskMessage(message, pattern, LOMBOK_REPLACEMENT_REGEX);
    }
}