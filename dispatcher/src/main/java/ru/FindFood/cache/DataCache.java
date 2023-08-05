package ru.FindFood.cache;

import ru.FindFood.botapi.BotState;
import ru.FindFood.entities.User;

//Отвечает за общение бота с разными пользователями
public interface DataCache {
    void setUsersCurrentBotState(Long userId, BotState botState);

    BotState getUsersCurrentBotState(Long userId);

    User getUser(Long userId);

    void saveUser(Long userId, User user);
}
