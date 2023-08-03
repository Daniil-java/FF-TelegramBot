package ru.FindFood.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "integrations")
@Data
public class ServiceIntegrationProperties {
    private Integer connectTimeout;
    private Integer readTimeout;
    private Integer writeTimeout;
}
