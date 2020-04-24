package com.slalom.logging.compliance.logback;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Employee {
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String ssn;
}
