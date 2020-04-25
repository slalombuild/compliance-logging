package com.slalom.logging.compliance.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slalom.logging.compliance.common.MaskType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleLogbackExample {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleLogbackExample.class);

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
