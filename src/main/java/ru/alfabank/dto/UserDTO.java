package ru.alfabank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String login;
    private String password;
    private String checkPassword; //поле для подтверждения при регистрации или новый пароль при смене
}
