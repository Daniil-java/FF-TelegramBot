package ru.FindFood.cache;


import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.FindFood.botapi.BotState;
import ru.FindFood.entities.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Component
@Slf4j
@NoArgsConstructor
public class UserDataCache implements DataCache {    //Прослойка между БД и приложением
    private Map<Long, BotState> usersBotStates = new HashMap<>();
    private Map<Long, User> usersData = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(Long userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(Long userId) {
        BotState botState = usersBotStates.get(userId);
        log.debug(botState.toString());
        if (Objects.isNull(botState)) {
            botState = BotState.START;
        }

        return botState;
    }

    @Override
    public User getUser(Long userId) {
        User user = usersData.get(userId);
        if (user == null) {
            user = new User();
        }
        return user;
    }

    @Override
    public void saveUser(Long userId, User user) {
        usersData.put(userId, user);
    }

}
