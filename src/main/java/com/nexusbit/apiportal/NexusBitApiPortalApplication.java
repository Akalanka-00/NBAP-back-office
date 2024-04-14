package com.nexusbit.apiportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class NexusBitApiPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexusBitApiPortalApplication.class, args);
    }

}
