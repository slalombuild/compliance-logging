/**
 * MIT License
 *
 * <p>Copyright (c) 2020 Slalom LLC
 *
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * <p>The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
