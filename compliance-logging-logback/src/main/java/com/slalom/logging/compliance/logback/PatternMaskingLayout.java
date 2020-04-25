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
package com.slalom.logging.compliance.logback;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.slalom.logging.compliance.common.MaskService;
import com.slalom.logging.compliance.common.MaskType;
import com.slalom.logging.compliance.common.impl.JsonMaskMaskService;
import com.slalom.logging.compliance.common.impl.LombokMaskMaskService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Getter;
import org.slf4j.Marker;

public class PatternMaskingLayout extends PatternLayout {

  @Getter(AccessLevel.PACKAGE)
  private boolean enabled = false;

  private MaskService jsonMaskService = new JsonMaskMaskService(Collections.emptyList());
  private MaskService lombokMaskService = new LombokMaskMaskService(Collections.emptyList());

  /**
   * Method called by logback with fields provided in the configuration file.
   *
   * @param fields the fields to be masked
   */
  public void addFields(final String fields) {
    final List<String> regex =
        Stream.of(fields.split(",")).map(String::trim).collect(Collectors.toList());
    enabled = true;
    jsonMaskService = new JsonMaskMaskService(regex);
    lombokMaskService = new LombokMaskMaskService(regex);
  }

  @Override
  public String doLayout(final ILoggingEvent event) {
    final String message = super.doLayout(event);
    if (enabled) {
      final Marker marker = event.getMarker();
      return marker == null ? message : maskMessage(message, marker);
    } else {
      return message;
    }
  }

  private String maskMessage(final String message, final Marker marker) {
    String maskedMessage = message;
    if (MaskType.JSON.getName().equals(marker.getName())) {
      maskedMessage = jsonMaskService.maskMessage(message);
    } else if (MaskType.LOMBOK.getName().equals(marker.getName())) {
      maskedMessage = lombokMaskService.maskMessage(message);
    }
    return maskedMessage;
  }
}
