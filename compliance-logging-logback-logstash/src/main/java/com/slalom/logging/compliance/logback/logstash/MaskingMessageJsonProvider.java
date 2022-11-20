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
package com.slalom.logging.compliance.logback.logstash;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
import com.slalom.logging.compliance.core.MaskService;
import com.slalom.logging.compliance.core.MaskType;
import com.slalom.logging.compliance.core.impl.JsonMaskService;
import com.slalom.logging.compliance.core.impl.LombokMaskService;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Getter;
import net.logstash.logback.composite.AbstractFieldJsonProvider;
import net.logstash.logback.composite.FieldNamesAware;
import net.logstash.logback.composite.JsonWritingUtils;
import net.logstash.logback.fieldnames.LogstashFieldNames;
import org.slf4j.Marker;

/** Logtsash encoder in charge of calling the right service to mask sensitive data. */
public class MaskingMessageJsonProvider extends AbstractFieldJsonProvider<ILoggingEvent>
    implements FieldNamesAware<LogstashFieldNames> {

  @Getter(AccessLevel.PACKAGE)
  private boolean enabled = false;

  private MaskService jsonMaskService = new JsonMaskService(Collections.emptyList());
  private MaskService lombokMaskService = new LombokMaskService(Collections.emptyList());

  private static final String FIELD_NAME = "msg";

  public MaskingMessageJsonProvider() {
    setFieldName(FIELD_NAME);
  }

  /**
   * Method called by logback with fields provided in the configuration file.
   *
   * @param fields the fields to be masked
   */
  public void addFields(final String fields) {
    final List<String> regex =
        Stream.of(fields.split(",")).map(String::trim).collect(Collectors.toList());
    enabled = true;
    jsonMaskService = new JsonMaskService(regex);
    lombokMaskService = new LombokMaskService(regex);
  }

  @Override
  public void setFieldNames(final LogstashFieldNames fieldNames) {
    setFieldName(fieldNames.getMessage());
  }

  @Override
  public void writeTo(final JsonGenerator generator, final ILoggingEvent event) throws IOException {
    final String message = event.getFormattedMessage();
    if (enabled) {
      final Marker marker = event.getMarker();
      if (marker == null) {
        JsonWritingUtils.writeStringField(generator, getFieldName(), message);
      } else {
        writeMaskMessageTo(generator, message, marker);
      }
    } else {
      JsonWritingUtils.writeStringField(generator, getFieldName(), message);
    }
  }

  private void writeMaskMessageTo(
      final JsonGenerator generator, final String message, final Marker marker) throws IOException {
    String maskedMessage = message;
    if (MaskType.JSON.getName().equals(marker.getName())) {
      maskedMessage = jsonMaskService.maskMessage(message);
    } else if (MaskType.LOMBOK.getName().equals(marker.getName())) {
      maskedMessage = lombokMaskService.maskMessage(message);
    }
    JsonWritingUtils.writeStringField(generator, getFieldName(), maskedMessage);
  }
}
