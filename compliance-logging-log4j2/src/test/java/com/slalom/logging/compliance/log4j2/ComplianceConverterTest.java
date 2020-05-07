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
package com.slalom.logging.compliance.log4j2;

import static org.assertj.core.api.Assertions.assertThat;

import com.slalom.logging.compliance.core.MaskType;
import com.slalom.logging.compliance.log4j2.stub.LogEventStub;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.LogEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ComplianceConverterTest {

  @DisplayName("Check that the converter is disabled when an empty array of options is sent")
  @Test
  public void testCheckConverterStateDisabled() {
    // given
    String[] options = new String[] {};

    // when
    ComplianceConverter converter = ComplianceConverter.newInstance(options);

    // then
    assertThat(converter.isEnabled()).isFalse();
  }

  @DisplayName(
      "Check that the converter is disabled when an array containing an empty string is sent")
  @Test
  public void testCheckConverterStateDisabled2() {
    // given
    String[] options = new String[] {""};

    // when
    ComplianceConverter converter = ComplianceConverter.newInstance(options);

    // then
    assertThat(converter.isEnabled()).isFalse();
  }

  @DisplayName("Check that the converter is disabled when the option sent start by ${")
  @Test
  public void testCheckConverterStateDisabled3() {
    // given
    String[] options = new String[] {"${REGEX}"};

    // when
    ComplianceConverter converter = ComplianceConverter.newInstance(options);

    // then
    assertThat(converter.isEnabled()).isFalse();
  }

  @DisplayName("Check that the converter is enabled when a correct option is sent")
  @Test
  public void testCheckConverterStateEnabled() {
    // given
    String[] options = new String[] {"field"};

    // when
    ComplianceConverter converter = ComplianceConverter.newInstance(options);

    // then
    assertThat(converter.isEnabled()).isTrue();
  }

  @DisplayName("Check that the converter is masking json strings")
  @Test
  public void testMaskJsonMessage() {
    // given
    String[] options = new String[] {"password,ssn"};
    String message =
        "my message with json {\"login\":\"john\",\"password\":\"mypassword\",\"ssn\":\"123-12-1234\"}";
    ComplianceConverter converter = ComplianceConverter.newInstance(options);
    Marker marker = MarkerManager.getMarker(MaskType.JSON_MARKER_NAME);
    LogEvent logEvent = new LogEventStub(message, marker);
    StringBuilder outputMessage = new StringBuilder();

    // when
    converter.format(logEvent, outputMessage);

    // then
    String actual = outputMessage.toString();
    assertThat(actual)
        .isEqualTo(
            "my message with json {\"login\":\"john\",\"password\":\"***********\",\"ssn\":\"***********\"}");
  }

  @DisplayName("Check that the converter is masking lombok strings")
  @Test
  public void testMaskLombokMessage() {
    // given
    String[] options = new String[] {"password,ssn"};
    String message =
        "my message with lombok "
            + Employee.builder().firstName("john").lastName("doe").ssn("123-12-1234").build();
    ComplianceConverter converter = ComplianceConverter.newInstance(options);
    Marker marker = MarkerManager.getMarker(MaskType.LOMBOK_MARKER_NAME);
    LogEvent logEvent = new LogEventStub(message, marker);
    StringBuilder outputMessage = new StringBuilder();

    // when
    converter.format(logEvent, outputMessage);

    // then
    String actual = outputMessage.toString();
    assertThat(actual)
        .isEqualTo(
            "my message with lombok Employee(firstName=john, lastName=doe, ssn=***********)");
  }

  @DisplayName("Check that the converter is not doing anything when no marker is used")
  @Test
  public void testWithoutMarker() {
    // given
    String[] options = new String[] {"password,ssn"};
    String message =
        "my message with json {\"login\":\"john\",\"password\":\"mypassword\",\"ssn\":\"123-12-1234\"}";
    ComplianceConverter converter = ComplianceConverter.newInstance(options);
    LogEvent logEvent = new LogEventStub(message);
    StringBuilder outputMessage = new StringBuilder();

    // when
    converter.format(logEvent, outputMessage);

    // then
    String actual = outputMessage.toString();
    assertThat(actual).isEqualTo(message);
  }

  @DisplayName("Check that the converter is not doing anything when it is disabled")
  @Test
  public void testWithPluginDisabled() {
    // given
    String[] options = new String[] {""};
    String message =
        "my message with json {\"login\":\"john\",\"password\":\"mypassword\",\"ssn\":\"123-12-1234\"}";
    ComplianceConverter converter = ComplianceConverter.newInstance(options);
    LogEvent logEvent = new LogEventStub(message);
    StringBuilder outputMessage = new StringBuilder();

    // when
    converter.format(logEvent, outputMessage);

    // then
    String actual = outputMessage.toString();
    assertThat(actual).isEqualTo(message);
  }
}
