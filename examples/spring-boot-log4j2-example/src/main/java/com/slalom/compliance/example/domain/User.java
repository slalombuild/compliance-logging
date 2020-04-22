package com.slalom.compliance.example.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class User {
    private final String login;
    private final String password;
    private final String ssn;
}
