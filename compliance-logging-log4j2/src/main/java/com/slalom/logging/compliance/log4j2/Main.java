package com.slalom.logging.compliance.log4j2;

import com.slalom.logging.compliance.common.MaskService;

public class Main {

    MaskService maskService = new MaskService() {
        @Override
        public String maskMessage(String message) {
            return null;
        }
    };
}
