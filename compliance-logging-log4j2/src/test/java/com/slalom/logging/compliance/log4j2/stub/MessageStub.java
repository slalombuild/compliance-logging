package com.slalom.logging.compliance.log4j2.stub;

import org.apache.logging.log4j.message.Message;

public class MessageStub implements Message {

    private final String message;

    public MessageStub(final String message) {
        this.message = message;
    }

    @Override
    public String getFormattedMessage() {
        return message;
    }

    @Override
    public String getFormat() {
        return null;
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }
}
