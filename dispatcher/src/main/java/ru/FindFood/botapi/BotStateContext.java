package ru.FindFood.botapi;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
    private Map<BotState, InputMessageHandler> messageHandlers = new EnumMap<>(BotState.class);

    //Заполняет мапу
    public BotStateContext(List<InputMessageHandler> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    //Распределение запроса по обработчикам
    public SendMessage processInputMessage(BotState currentState, Message message) {
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }

    private InputMessageHandler findMessageHandler(BotState currentState) {
        if (isStarting(currentState)) {
            return messageHandlers.get(BotState.START);
        }
        return messageHandlers.get(currentState);
    }

    private boolean isStarting(BotState currentState) {
        switch (currentState) {
            case START:
                return true;
            default:
                return false;
        }
    }

}
