package com.slalom.logging.compliance.logback.logstash;

import static org.assertj.core.api.Assertions.assertThat;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.slalom.logging.compliance.core.MaskType;
import com.slalom.logging.compliance.logback.logstash.stub.JsonGeneratorStub;
import com.slalom.logging.compliance.logback.logstash.stub.LoggingEventStub;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class MaskingMessageJsonProviderTest {

  @DisplayName("Check that the masking is disabled when addFields method is not called")
  @Test
  public void testCheckStateDisabled() {
    // when
    MaskingMessageJsonProvider provider = new MaskingMessageJsonProvider();

    // then
    assertThat(provider.isEnabled()).isFalse();
  }

  @DisplayName("Check that the masking is disabled when addFields method is not called")
  @Test
  public void testCheckStateDisabled2() {
    // given
    MaskingMessageJsonProvider provider = new MaskingMessageJsonProvider();

    // when
    provider.addFields("password,ssn");

    // then
    assertThat(provider.isEnabled()).isTrue();
  }

  @DisplayName("Check that the encoder is masking json strings")
  @Test
  public void testMaskJsonMessage() throws IOException {
    // given
    String fields = "password,ssn";
    String message =
        "my message with json {\"login\":\"john\",\"password\":\"mypassword\",\"ssn\":\"123-12-1234\"}";
    MaskingMessageJsonProvider provider = buildProvider(fields);
    Marker marker = MarkerFactory.getMarker(MaskType.JSON_MARKER_NAME);
    ILoggingEvent logEvent = new LoggingEventStub(message, marker);
    JsonGeneratorStub jsonGenerator = new JsonGeneratorStub();

    // when
    provider.writeTo(jsonGenerator, logEvent);

    // then
    assertThat(jsonGenerator.getText())
        .contains(
            "my message with json {\"login\":\"john\",\"password\":\"***********\",\"ssn\":\"***********\"}");
  }

  @DisplayName("Check that the encoder is masking lombok strings")
  @Test
  public void testMaskLombokMessage() throws IOException {
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
    MaskingMessageJsonProvider provider = buildProvider(fields);
    Marker marker = MarkerFactory.getMarker(MaskType.LOMBOK_MARKER_NAME);
    ILoggingEvent logEvent = new LoggingEventStub(message, marker);
    JsonGeneratorStub jsonGenerator = new JsonGeneratorStub();

    // when
    provider.writeTo(jsonGenerator, logEvent);

    // then
    assertThat(jsonGenerator.getText())
        .contains(
            "my message with lombok Employee(firstName=john, lastName=doe, password=***********, ssn=***********)");
  }

  @DisplayName("Check that the encoder is not doing anything when no marker is used")
  @Test
  public void testWithoutMarker() throws IOException {
    // given
    String fields = "password,ssn";
    String message =
        "my message with json {\"login\":\"john\",\"password\":\"mypassword\",\"ssn\":\"123-12-1234\"}";
    MaskingMessageJsonProvider provider = buildProvider(fields);
    ILoggingEvent logEvent = new LoggingEventStub(message);
    JsonGeneratorStub jsonGenerator = new JsonGeneratorStub();

    // when
    provider.writeTo(jsonGenerator, logEvent);

    // then
    assertThat(jsonGenerator.getText()).contains(message);
  }

  @DisplayName("Check that the encoder is not doing anything when it is disabled")
  @Test
  public void testWithPluginDisabled() throws IOException {
    // given
    String message =
        "my message with json {\"login\":\"john\",\"password\":\"mypassword\",\"ssn\":\"123-12-1234\"}";
    MaskingMessageJsonProvider provider = buildDisabledProvider();
    ILoggingEvent logEvent = new LoggingEventStub(message);
    JsonGeneratorStub jsonGenerator = new JsonGeneratorStub();

    // when
    provider.writeTo(jsonGenerator, logEvent);

    // then
    assertThat(jsonGenerator.getText()).contains(message);
  }

  private MaskingMessageJsonProvider buildProvider(String fields) {
    MaskingMessageJsonProvider provider = new MaskingMessageJsonProvider();
    provider.addFields(fields);
    return provider;
  }

  private MaskingMessageJsonProvider buildDisabledProvider() {
    return new MaskingMessageJsonProvider();
  }
}
