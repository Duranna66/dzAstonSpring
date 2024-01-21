package ru.alfabank.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alfabank.dto.UserDTO;
import ru.alfabank.entities.User;
import ru.alfabank.entities.enums.UserState;
import ru.alfabank.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserService {
    @Autowired
    private UserMapper userMapper;
    private  List<User> users = new ArrayList<>();
//    static {
//        users.add(User.builder()
//                .login("test").build());
//    }
    public User save(UserDTO userDTO) {
        User user = userMapper.mapFromUserDTOtoUser(userDTO); //маплю
        user.changeOrAddState(UserState.Registered); //меняю статус у пользака
        users.add(user); //добавляю в список(бд)
        return user;
    }
    public User checkExistUserOrNot(UserDTO userDTO) {
        for(User u : users) { //ищу пользака если уже есть
            if(u.getLogin().equals(userDTO.getLogin())) {
                return u;
            }
        }
        return null;
    }
    public boolean checkEqualsPasswords(UserDTO userDTO) {
        return userDTO.getPassword().equals(userDTO.getCheckPassword()); //проверка на  совпадение паролей
    }
    public boolean allCheck(UserDTO userDTO) { //все проверки(для удобства)
        return checkEqualsPasswords(userDTO) && checkExistUserOrNot(userDTO) != null;
    }
    public User updatePassword(UserDTO userDTO) {
        User user = checkExistUserOrNot(userDTO);//проверка на зареган или нет пользак
        user.setPassword(userDTO.getCheckPassword()); //просто меняю
        return user;
    }
    public List<User> showAllUsers() {
        return users;
    } //показываю всех
    public User logIn(UserDTO userDTO) {
        User user = checkExistUserOrNot(userDTO); //етсь или нет в бд
        if(user == null) {
            return null;
        }
        user.changeOrAddState(UserState.Active); //меняю на статус аля в сети
        return user;
    }

}
