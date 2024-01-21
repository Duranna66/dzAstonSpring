package ru.alfabank.mappers;

import ru.alfabank.dto.UserDTO;
import ru.alfabank.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapFromUserDTOtoUser(UserDTO userDTO) { //обычный мапер
        User user = User.builder()
                .login(userDTO.getLogin())
                .password(userDTO.getPassword()).build();
        return user;
    }
}
