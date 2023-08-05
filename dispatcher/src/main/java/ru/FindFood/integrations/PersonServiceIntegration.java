package ru.FindFood.integrations;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import ru.FindFood.dtos.PersonDto;
import ru.FindFood.properties.ServiceIntegrationProperties;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(
        {ServiceIntegrationProperties.class}
)
public class PersonServiceIntegration {
    private WebClient webClient;

    private final ServiceIntegrationProperties sip;

    @Value("${integrations.person-service.url}")
    private String personServiceUrl;

    public PersonDto getMenuByTelegramName(String username) {
        return getWebClient().get()
                .uri("/ff-personal/api/v1/persons/byTelegramName")
                .header("username", username)
                .retrieve()
                .bodyToMono(PersonDto.class)
                .block();
    }


    private WebClient getWebClient() {
        if (webClient == null) {
            HttpClient httpClient = HttpClient.create()
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, sip.getConnectTimeout())
                    .doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(sip.getReadTimeout(), TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(sip.getWriteTimeout(), TimeUnit.MILLISECONDS)));
            webClient = WebClient.builder()
                    .baseUrl(personServiceUrl)
                    .clientConnector(new ReactorClientHttpConnector(httpClient))
                    .build();
        }
        return webClient;
    }
}
