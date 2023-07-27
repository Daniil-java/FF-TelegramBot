package ru.FindFood.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "integrations.person-service")
@Data
public class MenuServiceIntegrationProperties {
    private String url;
    private Integer connectTimeout;
    private Integer readTimeout;
    private Integer writeTimeout;
}
