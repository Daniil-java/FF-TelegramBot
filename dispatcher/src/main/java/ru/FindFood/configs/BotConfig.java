package ru.FindFood.configs;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.FindFood.properties.ServiceIntegrationProperties;

@Configuration
@Data
@PropertySource("application.properties")
@EnableConfigurationProperties(
        ServiceIntegrationProperties.class
)
@RequiredArgsConstructor
public class BotConfig {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String token;


}