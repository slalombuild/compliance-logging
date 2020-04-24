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

import static org.assertj.core.api.Assertions.assertThat;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.slalom.logging.compliance.common.MaskType;
import com.slalom.logging.compliance.logback.stub.LoggingEventStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class PatternMaskingLayoutTest {

  private static final String PATTERN = "%d %le [%t] %lo{30} - %m%n";
  private static final LoggerContext LOGGER_CONTEXT = new LoggerContext();

  @DisplayName("Check that the layout is disabled when addFields method is not called")
  @Test
  public void testCheckLayoutStateDisabled() {
    // when
    PatternMaskingLayout layout = new PatternMaskingLayout();

    // then
    assertThat(layout.isEnabled()).isFalse();
  }

  @DisplayName("Check that the layout is enabled when addFields method is called")
  @Test
  public void testCheckConverterStateDisabled2() {
    // given
    PatternMaskingLayout layout = new PatternMaskingLayout();

    // when
    layout.addFields("password,ssn");

    // then
    assertThat(layout.isEnabled()).isTrue();
  }

  @DisplayName("Check that the layout is masking json strings")
  @Test
  public void testMaskJsonMessage() {
    // given
    String fields = "password,ssn";
    String message =
        "my message with json {\"login\":\"john\",\"password\":\"mypassword\",\"ssn\":\"123-12-1234\"}";
    PatternMaskingLayout layout = buildLayout(fields);
    Marker marker = MarkerFactory.getMarker(MaskType.JSON_MARKER_NAME);
    ILoggingEvent logEvent = new LoggingEventStub(message, marker);

    // when
    String actual = layout.doLayout(logEvent);

    // then
    assertThat(actual)
        .contains(
            "my message with json {\"login\":\"john\",\"password\":\"***********\",\"ssn\":\"***********\"}");
  }

  @DisplayName("Check that the layout is masking lombok strings")
  @Test
  public void testMaskLombokMessage() {
    // given
    String fields = "password,ssn";
    String message =
        "my message with lombok "
            + Employee.builder()
                .firstName("john")
                .lastName("doe")
                .password("mypassword")
                .ssn("123-12-1234")
                .build();
    PatternMaskingLayout layout = buildLayout(fields);
    Marker marker = MarkerFactory.getMarker(MaskType.LOMBOK_MARKER_NAME);
    ILoggingEvent logEvent = new LoggingEventStub(message, marker);

    // when
    String actual = layout.doLayout(logEvent);

    // then
    assertThat(actual)
        .contains(
            "my message with lombok Employee(firstName=john, lastName=doe, password=***********, ssn=***********)");
  }

  @DisplayName("Check that the layout is not doing anything when no marker is used")
  @Test
  public void testWithoutMarker() {
    // given
    String fields = "password,ssn";
    String message =
        "my message with json {\"login\":\"john\",\"password\":\"mypassword\",\"ssn\":\"123-12-1234\"}";
    PatternMaskingLayout layout = buildLayout(fields);
    ILoggingEvent logEvent = new LoggingEventStub(message);

    // when
    String actual = layout.doLayout(logEvent);

    // then
    assertThat(actual).contains(message);
  }

  @DisplayName("Check that the layout is not doing anything when it is disabled")
  @Test
  public void testWithPluginDisabled() {
    // given
    String message =
        "my message with json {\"login\":\"john\",\"password\":\"mypassword\",\"ssn\":\"123-12-1234\"}";
    PatternMaskingLayout layout = buildDisabledLayout();
    ILoggingEvent logEvent = new LoggingEventStub(message);

    // when
    String actual = layout.doLayout(logEvent);

    // then
    assertThat(actual).contains(message);
  }

  public PatternMaskingLayout buildLayout(final String fields) {
    final PatternMaskingLayout layout = new PatternMaskingLayout();
    layout.setPattern(PATTERN);
    layout.setContext(LOGGER_CONTEXT);
    layout.start();
    layout.addFields(fields);
    return layout;
  }

  public PatternMaskingLayout buildDisabledLayout() {
    final PatternMaskingLayout layout = new PatternMaskingLayout();
    layout.setPattern(PATTERN);
    layout.setContext(LOGGER_CONTEXT);
    layout.start();
    return layout;
  }
}
