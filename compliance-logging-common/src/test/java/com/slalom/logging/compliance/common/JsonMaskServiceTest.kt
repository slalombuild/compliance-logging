package com.slalom.logging.compliance.common

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class JsonMaskServiceTest {

    @ParameterizedTest(name = "Fields: {0}, message {1} should be masked and return {2}")
    @MethodSource("source")
    fun `validate json mask service`(fields: List<String>, message: String, expected: String) {
        // given
        val maskService = JsonMaskService(fields)

        // when
        val actual = maskService.maskMessage(message)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        fun source(): Stream<Arguments> {
            return Stream.of(
                    arguments(listOf("password"), "{\"password\": \"1234\"}", "{\"password\": \"***********\"}"),
                    arguments(listOf("password"), "My Log    {\"password\": \"1234\"}", "My Log    {\"password\": \"***********\"}"),
                    arguments(listOf("password"), "My Log{\"password\": \"1234\"}     ", "My Log{\"password\": \"***********\"}     "),
                    arguments(listOf("password"), "{\"password\":    \"1234\"}", "{\"password\":    \"***********\"}")
            )
        }
    }
}