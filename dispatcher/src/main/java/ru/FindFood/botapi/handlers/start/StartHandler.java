package ru.FindFood.botapi.handlers.start;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.FindFood.botapi.BotState;
import ru.FindFood.botapi.InputMessageHandler;
import ru.FindFood.cache.UserDataCache;
import ru.FindFood.dtos.PersonDto;
import ru.FindFood.entities.User;
import ru.FindFood.integrations.PersonServiceIntegration;
import ru.FindFood.repositories.UserRepository;

import java.sql.Timestamp;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartHandler implements InputMessageHandler {

    private final UserRepository userRepository;
    private final UserDataCache userDataCache;

    private final PersonServiceIntegration personSI;

    @Override
    public SendMessage handle(Message message) {
        var userId = message.getFrom().getId();
        long chatId = message.getChatId();
        var username = message.getFrom().getUserName();
        var firstname = message.getFrom().getFirstName();
        var lastname = message.getFrom().getLastName();

        BotState botState = userDataCache.getUsersCurrentBotState(chatId);

//        TODO Настроить БД. Перейти от кэша к БД. Перейти к исп. Optional

        User user = new User().setUserName(username)
                .setChatId(chatId)
                .setFirstName(firstname)
                .setLastName(lastname)
                .setBotState(botState);

        SendMessage replyMessage = new SendMessage(String.valueOf(chatId), "Пожалуйста, проверьте, что вы зарегестрированы на сайте");
        if (personSI.getMenuByTelegramName(username) instanceof PersonDto) {
            if (botState.equals(BotState.START)) {
                userDataCache.setUsersCurrentBotState(userId, BotState.START_END);
                registerUser(message);
                replyMessage.setText("Здравствуйте");
            }
            user.setRegistred(true);
            userRepository.save(user);
            userDataCache.setUsersCurrentBotState(userId, BotState.START_END);
            return replyMessage;
        } else {
            return replyMessage;
        }
    }

    @Override
    public BotState getHandlerName() {
        return BotState.START;
    }

    private void registerUser(Message message) { //Регистрация пользователя в БД
        if (userRepository.findById(message.getChatId()).isEmpty()) {
            var chatId = message.getChatId();
            var chat = message.getChat();

            User user = new User();

            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegistredAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(user);
            log.info("user saved: " + user);
        }
    }
}
