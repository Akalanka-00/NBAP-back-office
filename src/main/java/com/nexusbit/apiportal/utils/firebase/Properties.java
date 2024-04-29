package com.nexusbit.apiportal.utils.firebase;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "firebase")
public class Properties {

    private String bucketName;
    private String imageUrl;
}
