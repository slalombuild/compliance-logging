package com.slalom.compliance.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slalom.compliance.example.domain.User;
import com.slalom.logging.compliance.common.MaskType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void postConstruct() throws JsonProcessingException {
        final User user = User.builder()
                .login("mylogin")
                .password("mypassword")
                .ssn("123-123-1234")
                .build();
        log.info("No Marker: {}", user);
        log.info(MaskType.LOMBOK, "Marker Lombok: {}", user);
        log.info(MaskType.JSON, "Marker Json: {}", new ObjectMapper().writeValueAsString(user));
    }
}
