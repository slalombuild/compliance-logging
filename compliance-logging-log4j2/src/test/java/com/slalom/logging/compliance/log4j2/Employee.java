package com.slalom.logging.compliance.log4j2;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Employee {
    private final String firstName;
    private final String lastName;
    private final String ssn;
}
