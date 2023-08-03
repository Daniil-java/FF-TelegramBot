package ru.FindFood.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.FindFood.botapi.BotState;
import ru.FindFood.botapi.BotStateContext;
import ru.FindFood.cache.UserDataCache;

@Component
@AllArgsConstructor
@Slf4j
public class BotService {
    private BotStateContext botStateContext;    //Управление состояниями
    private UserDataCache userDataCache;    //Хранилище информации пользователей

    public SendMessage handleUpdate(Update update) {
        log.debug("BotService.handleUpdate()");
        SendMessage replyMessage = null;

        Message message = update.getMessage();

        if (message != null && message.hasText()) {                 //Проверка на текстовое сообщение
            log.info("New message from User:{}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        var userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;

        switch (inputMsg) {
            case "/start":
                botState = BotState.START;
                break;
            case "/menu":
                botState = BotState.GET_MENU;
                break;
            default:
                botState = userDataCache.getUsersCurrentBotState(userId); //Отправляет пользователя на регистрацию
                break;
        }
        userDataCache.setUsersCurrentBotState(userId, botState);    // "Регистрирует" пользователя

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }

}
