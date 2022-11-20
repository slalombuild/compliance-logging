/**
 * MIT License
 *
 * <p>Copyright (c) 2020 Slalom LLC
 *
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * <p>The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.slalom.logging.compliance.logback.logstash.stub;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import org.slf4j.Marker;
import org.slf4j.event.KeyValuePair;

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
  public List<Marker> getMarkerList() {
    return null;
  }

  @Override
  public Map<String, String> getMDCPropertyMap() {
    return null;
  }

  @SuppressWarnings("deprecation")
  @Override
  public Map<String, String> getMdc() {
    return null;
  }

  @Override
  public long getTimeStamp() {
    return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
  }

  @Override
  public int getNanoseconds() {
    return 0;
  }

  @Override
  public long getSequenceNumber() {
    return 0;
  }

  @Override
  public List<KeyValuePair> getKeyValuePairs() {
    return null;
  }

  @Override
  public void prepareForDeferredProcessing() {}
}
