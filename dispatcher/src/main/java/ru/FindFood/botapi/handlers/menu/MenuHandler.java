package ru.FindFood.botapi.handlers.menu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.FindFood.botapi.BotState;
import ru.FindFood.botapi.InputMessageHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class MenuHandler implements InputMessageHandler {
    @Override
    public SendMessage handle(Message message) {
        return null;
    }

    @Override
    public BotState getHandlerName() {
        return null;
    }
}
