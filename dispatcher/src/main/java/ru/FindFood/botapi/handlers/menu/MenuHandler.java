package ru.FindFood.botapi.handlers.menu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.FindFood.botapi.BotState;
import ru.FindFood.botapi.InputMessageHandler;
import ru.FindFood.cache.UserDataCache;
import ru.FindFood.dtos.MenuDto;
import ru.FindFood.integrations.MenuServiceIntegration;
import ru.FindFood.repositories.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class MenuHandler implements InputMessageHandler {

    private final UserRepository userRepository;
    private final UserDataCache userDataCache;
    private final MenuServiceIntegration menuSIP;

    @Override
    public SendMessage handle(Message message) {
        var userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String username = message.getFrom().getUserName();
        BotState botState = userDataCache.getUsersCurrentBotState(chatId);

        SendMessage replyMessage = new SendMessage(String.valueOf(chatId), "Пожалуйста, проверьте, что вы зарегестрированы на сайте");

        if (userRepository.getUserByUserName(username).get().isRegistred()) {   //TODO Обработчик Optional

            if (botState.equals(BotState.GET_MENU)) {
                userDataCache.setUsersCurrentBotState(userId, BotState.START_END);
                MenuDto menuDto = menuSIP.getMenuByTelegramName(message.getFrom().getUserName());
                replyMessage.setText(menuDto.toString());
            }

            userDataCache.setUsersCurrentBotState(userId, BotState.START_END);
            return replyMessage;

        } else {
            return replyMessage;
        }

    }

    @Override
    public BotState getHandlerName() {
        return BotState.GET_MENU;
    }


}
