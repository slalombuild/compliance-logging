package com.slalom.logging.compliance.common;

import com.google.common.collect.ImmutableList;
import com.slalom.logging.compliance.common.impl.JsonMaskService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class JsonMaskServiceTest {

    @ParameterizedTest(name = "Fields: {0}, message {1} should be masked and return {2}")
    @MethodSource("source")
    public void testJsonMasking(List<String> fields, String message, String expected) {
        // given
        MaskService maskService = new JsonMaskService(fields);

        // when
        String actual = maskService.maskMessage(message);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> source() {
        final User user = User.builder().login("john").password("1234").ssn("123-12-1234").build();
        return Stream.of(
                arguments(ImmutableList.of("password"), user.toJson(), "{\"login\":\"john\",\"password\":\"***********\",\"ssn\":\"123-12-1234\"}"),
                arguments(ImmutableList.of("password", "ssn"), user.toJson(), "{\"login\":\"john\",\"password\":\"***********\",\"ssn\":\"***********\"}"),
                arguments(ImmutableList.of("password", "ssn", "login"), "Log before: " + user.toJson(), "Log before: {\"login\":\"***********\",\"password\":\"***********\",\"ssn\":\"***********\"}"),
                arguments(ImmutableList.of("password"), "Log before    {\"password\": \"1234\"}", "Log before    {\"password\": \"***********\"}"),
                arguments(ImmutableList.of("password"), "Log before{\"password\": \"1234\"}     ", "Log before{\"password\": \"***********\"}     "),
                arguments(ImmutableList.of("password"), "{\"password\":    \"1234\"}", "{\"password\":    \"***********\"}"),
                arguments(ImmutableList.of("password"), "{\"password\":\"1234\"}            log after", "{\"password\":\"***********\"}            log after")
        );
    }
}
