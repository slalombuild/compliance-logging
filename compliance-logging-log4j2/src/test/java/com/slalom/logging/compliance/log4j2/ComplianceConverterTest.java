package com.slalom.logging.compliance.log4j2;

import com.slalom.logging.compliance.common.MaskType;
import com.slalom.logging.compliance.log4j2.stub.LogEventStub;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.LogEvent;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ComplianceConverterTest {

    @Test
    public void testCheckConverterStateDisabled() {
        // given
        String[] options = new String[]{};

        // when
        ComplianceConverter converter = ComplianceConverter.newInstance(options);

        // then
        assertThat(converter.isEnabled()).isFalse();
    }

    @Test
    public void testCheckConverterStateDisabled2() {
        // given
        String[] options = new String[]{""};

        // when
        ComplianceConverter converter = ComplianceConverter.newInstance(options);

        // then
        assertThat(converter.isEnabled()).isFalse();
    }

    @Test
    public void testCheckConverterStateDisabled3() {
        // given
        String[] options = new String[]{"${REGEX}"};

        // when
        ComplianceConverter converter = ComplianceConverter.newInstance(options);

        // then
        assertThat(converter.isEnabled()).isFalse();
    }

    @Test
    public void testCheckConverterStateEnabled() {
        // given
        String[] options = new String[]{"field"};

        // when
        ComplianceConverter converter = ComplianceConverter.newInstance(options);

        // then
        assertThat(converter.isEnabled()).isTrue();
    }

    @Test
    public void testMaskJsonMessage() {
        // given
        String[] options = new String[]{"password,ssn"};
        String message = "my message with json {\"login\":\"john\",\"password\":\"mypassword\",\"ssn\":\"123-12-1234\"}";
        ComplianceConverter converter = ComplianceConverter.newInstance(options);
        Marker marker = MarkerManager.getMarker(MaskType.JSON_MARKER_NAME);
        LogEvent logEvent = new LogEventStub(message, marker);
        StringBuilder outputMessage = new StringBuilder();

        // when
        converter.format(logEvent, outputMessage);

        // then
        String actual = outputMessage.toString();
        assertThat(actual).isEqualTo("my message with json {\"login\":\"john\",\"password\":\"***********\",\"ssn\":\"***********\"}");
    }

    @Test
    public void testMaskLombokMessage() {
        // given
        String[] options = new String[]{"password,ssn"};
        String message = "my message with lombok " + Employee.builder().firstName("john").lastName("doe").ssn("123-12-1234").build();
        ComplianceConverter converter = ComplianceConverter.newInstance(options);
        Marker marker = MarkerManager.getMarker(MaskType.LOMBOK_MARKER_NAME);
        LogEvent logEvent = new LogEventStub(message, marker);
        StringBuilder outputMessage = new StringBuilder();

        // when
        converter.format(logEvent, outputMessage);

        // then
        String actual = outputMessage.toString();
        assertThat(actual).isEqualTo("my message with lombok Employee(firstName=john, lastName=doe, ssn=***********)");
    }

    @Test
    public void testWithoutMarker() {
        // given
        String[] options = new String[]{"password,ssn"};
        String message = "my message with json {\"login\":\"john\",\"password\":\"mypassword\",\"ssn\":\"123-12-1234\"}";
        ComplianceConverter converter = ComplianceConverter.newInstance(options);
        LogEvent logEvent = new LogEventStub(message);
        StringBuilder outputMessage = new StringBuilder();

        // when
        converter.format(logEvent, outputMessage);

        // then
        String actual = outputMessage.toString();
        assertThat(actual).isEqualTo(message);
    }

    @Test
    public void testWithPluginDisabled() {
        // given
        String[] options = new String[]{""};
        String message = "my message with json {\"login\":\"john\",\"password\":\"mypassword\",\"ssn\":\"123-12-1234\"}";
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
