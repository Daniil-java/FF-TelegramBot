package ru.FindFood.botapi.handlers.start;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.FindFood.botapi.BotState;
import ru.FindFood.botapi.InputMessageHandler;
import ru.FindFood.cache.UserDataCache;
import ru.FindFood.entities.User;
import ru.FindFood.repositories.UserRepository;

import java.sql.Timestamp;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartHandler implements InputMessageHandler {

    private final UserRepository userRepository;
    private final UserDataCache userDataCache;

    @Override
    public SendMessage handle(Message message) {
        String userAnswer = message.getText();
        var userId = message.getFrom().getId();
        long chatId = message.getChatId();
        BotState botState = userDataCache.getUsersCurrentBotState(chatId);

//        User user = userRepository.findById(chatId).get();
//        TODO Настроить БД. Перейти от кэша к БД. Перейти к исп. Optional
        User user = userDataCache.getUser(chatId);
        SendMessage replyMessage = new SendMessage(String.valueOf(chatId), "Что-то пошло не так ¯\\_(ツ)_/¯");

        if (botState.equals(BotState.START)) {
            userDataCache.setUsersCurrentBotState(userId, BotState.START_END);
            registerUser(message);
            replyMessage.setText("Здравствуйте");
        }

        userRepository.save(user);
        userDataCache.setUsersCurrentBotState(userId, BotState.START_END);
        return replyMessage;
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
