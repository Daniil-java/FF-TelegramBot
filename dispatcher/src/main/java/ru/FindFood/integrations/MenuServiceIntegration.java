package ru.FindFood.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.FindFood.dtos.MenuDto;


@Component
@RequiredArgsConstructor
public class MenuServiceIntegration {
    private final WebClient menuServiceWebClient;

    public MenuDto getMenuByTelegramName(Update update) {
        return menuServiceWebClient.get()
                .uri("/api/v1/menus/all?telegramName=" + update.getMessage().getFrom().getUserName())
                .retrieve()
                .bodyToMono(MenuDto.class)
                .block();
    }
}
