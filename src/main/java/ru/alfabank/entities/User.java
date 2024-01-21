package ru.alfabank.entities;

import ru.alfabank.entities.enums.UserState;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Set<UserState> userStates;
    private String login;

    private String password;
    public void changeOrAddState(UserState userState) {
        if(userStates == null) {
            userStates = new HashSet<>();
        }
        userStates.add(userState);
    }
}
