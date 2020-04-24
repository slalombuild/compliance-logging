package com.slalom.logging.compliance.logback.stub;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;
import org.slf4j.Marker;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

public class LoggingEventStub implements ILoggingEvent {

    private final String message;
    private final Marker marker;

    public LoggingEventStub(final String message) {
        this.message = message;
        this.marker = null;
    }

    public LoggingEventStub(final String message, final Marker marker) {
        this.message = message;
        this.marker = marker;
    }

    @Override
    public String getThreadName() {
        return null;
    }

    @Override
    public Level getLevel() {
        return Level.INFO;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Object[] getArgumentArray() {
        return new Object[0];
    }

    @Override
    public String getFormattedMessage() {
        return message;
    }

    @Override
    public String getLoggerName() {
        return "logger";
    }

    @Override
    public LoggerContextVO getLoggerContextVO() {
        return null;
    }

    @Override
    public IThrowableProxy getThrowableProxy() {
        return null;
    }

    @Override
    public StackTraceElement[] getCallerData() {
        return new StackTraceElement[0];
    }

    @Override
    public boolean hasCallerData() {
        return false;
    }

    @Override
    public Marker getMarker() {
        return marker;
    }

    @Override
    public Map<String, String> getMDCPropertyMap() {
        return null;
    }

    @Override
    public Map<String, String> getMdc() {
        return null;
    }

    @Override
    public long getTimeStamp() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    }

    @Override
    public void prepareForDeferredProcessing() {

    }
}
