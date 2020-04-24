package com.slalom.logging.compliance.common;

import com.google.common.collect.ImmutableList;
import com.slalom.logging.compliance.common.impl.LombokMaskService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class LombokMaskServiceTest {

    @ParameterizedTest(name = "Fields: {0}, message {1} should be masked and return {2}")
    @MethodSource("source")
    public void test(List<String> fields, String message, String expected) {
        // given
        MaskService maskService = new LombokMaskService(fields);

        // when
        String actual = maskService.maskMessage(message);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> source() {
        final User user = User.builder().login("john").password("1234").ssn("123-12-1234").build();
        return Stream.of(
                arguments(ImmutableList.of("password"), "User: " + user, "User: User(login=john, password=***********, ssn=123-12-1234)"),
                arguments(ImmutableList.of("password", "ssn"), "User: " + user, "User: User(login=john, password=***********, ssn=***********)"),
                arguments(ImmutableList.of("password", "ssn", "login"), "User: " + user, "User: User(login=***********, password=***********, ssn=***********)"),
                arguments(ImmutableList.of("password", "ssn", "login"), user + " log after", "User(login=***********, password=***********, ssn=***********) log after")
        );
    }
}
