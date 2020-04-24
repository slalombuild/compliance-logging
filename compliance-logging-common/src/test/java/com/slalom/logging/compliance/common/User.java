package com.slalom.logging.compliance.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;

@Builder
@Getter
@ToString
public class User {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final String login;
    private final String password;
    private final String ssn;

    @SneakyThrows(JsonProcessingException.class)
    public String toJson() {
        return OBJECT_MAPPER.writeValueAsString(this);
    }
}
