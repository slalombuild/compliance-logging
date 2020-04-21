package com.slalom.logging.compliance.logback;

import com.slalom.logging.compliance.common.MaskService;

public class Main {

    MaskService maskService = new MaskService() {
        @Override
        public String maskMessage(String message) {
            return null;
        }
    };
}
