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
package com.slalom.logging.compliance.log4j2.stub;

import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.core.time.Instant;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.ReadOnlyStringMap;

public class LogEventStub implements LogEvent {

  private final Message message;
  private final Marker marker;

  public LogEventStub(final String message) {
    this.message = new MessageStub(message);
    this.marker = null;
  }

  public LogEventStub(final String message, final Marker marker) {
    this.message = new MessageStub(message);
    this.marker = marker;
  }

  @Override
  public LogEvent toImmutable() {
    return null;
  }

  @Override
  public Map<String, String> getContextMap() {
    return null;
  }

  @Override
  public ReadOnlyStringMap getContextData() {
    return null;
  }

  @Override
  public ThreadContext.ContextStack getContextStack() {
    return null;
  }

  @Override
  public String getLoggerFqcn() {
    return null;
  }

  @Override
  public Level getLevel() {
    return null;
  }

  @Override
  public String getLoggerName() {
    return null;
  }

  @Override
  public Marker getMarker() {
    return marker;
  }

  @Override
  public Message getMessage() {
    return message;
  }

  @Override
  public long getTimeMillis() {
    return 0;
  }

  @Override
  public Instant getInstant() {
    return null;
  }

  @Override
  public StackTraceElement getSource() {
    return null;
  }

  @Override
  public String getThreadName() {
    return null;
  }

  @Override
  public long getThreadId() {
    return 0;
  }

  @Override
  public int getThreadPriority() {
    return 0;
  }

  @Override
  public Throwable getThrown() {
    return null;
  }

  @Override
  public ThrowableProxy getThrownProxy() {
    return null;
  }

  @Override
  public boolean isEndOfBatch() {
    return false;
  }

  @Override
  public boolean isIncludeLocation() {
    return false;
  }

  @Override
  public void setEndOfBatch(boolean endOfBatch) {}

  @Override
  public void setIncludeLocation(boolean locationRequired) {}

  @Override
  public long getNanoTime() {
    return 0;
  }
}
