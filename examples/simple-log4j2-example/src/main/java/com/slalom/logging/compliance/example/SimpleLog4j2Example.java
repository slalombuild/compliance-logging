package com.slalom.logging.compliance.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slalom.logging.compliance.log4j2.mask.MaskType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleLog4j2Example {

  private static final Logger LOGGER = LogManager.getLogger(SimpleLog4j2Example.class);

  public static void main(String[] args) throws JsonProcessingException {
    final User user =
        User.builder().login("mylogin").password("mypassword").ssn("123-123-1234").build();
    LOGGER.info("================================================================================");
    LOGGER.info("No Marker:      {}", user);
    LOGGER.info(MaskType.LOMBOK, "Marker Lombok:  {}", user);
    LOGGER.info(MaskType.JSON, "Marker Json:    {}", new ObjectMapper().writeValueAsString(user));
    LOGGER.info("================================================================================");
  }

  @Data
  @ToString
  @Builder
  public static class User {
    private final String login;
    private final String password;
    private final String ssn;
  }
}
