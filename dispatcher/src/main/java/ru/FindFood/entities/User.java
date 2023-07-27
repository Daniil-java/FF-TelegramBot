package ru.FindFood.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import ru.FindFood.botapi.BotState;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "users")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class User {

    @Id
    @Column(name = "chatId")
    private Long chatId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "userName")
    private String userName;

    @Column(name = "botState")
    private BotState botState;

    @Column(name = "registredAt")
    private Timestamp registredAt;

    @Column(name = "isRegistred", columnDefinition = "boolean default false")
    private boolean isRegistred;

}
