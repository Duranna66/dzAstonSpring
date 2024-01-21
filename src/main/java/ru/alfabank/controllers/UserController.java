package ru.alfabank.controllers;

import org.springframework.http.MediaType;
import ru.alfabank.dto.UserDTO;
import ru.alfabank.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alfabank.entities.enums.UserState;
import ru.alfabank.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/registration" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registrateUser(@RequestBody UserDTO userDTO) { //регистрация пользаков
        if(!userService.allCheck(userDTO)) {
            return ResponseEntity.badRequest().body("your account already exist"); //ошибка если уже есть в бд
        }
        User user =  userService.save(userDTO); //если все ок сохраняем
        return ResponseEntity.ok(user.getLogin() + " was successfully registered)");
    }
    @PatchMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody UserDTO userDTO) { //обновка пароля приходит json'ом 3 поле по счету
        if(userService.checkExistUserOrNot(userDTO) == null) {  //если не зареган
            return ResponseEntity.badRequest().body("you have to reg acc");
        }
        if(userService.checkEqualsPasswords(userDTO)) { //если пароли сопадают
            return ResponseEntity.badRequest().body("new password and old are same");
        }
        userService.updatePassword(userDTO); //обновляем
        return ResponseEntity.ok("success");
    }
    @GetMapping("/getAllUsers")
    public ResponseEntity<String> getAllUsers() { //просто показываю всех пользаков
        List<User> users = userService.showAllUsers();
        return ResponseEntity.ok(users.toString());
    }
    @GetMapping("/login")
    public ResponseEntity<String> logIn(@RequestParam String login, @RequestParam String password) { //чел логинится, логин и пароль приходит в параметрах
        UserDTO userDTO = UserDTO.builder().login(login)
                        .password(password).build();
        if(userService.logIn(userDTO) == null) { //проверка зареган или нет
            return ResponseEntity.badRequest().body("you have to reg acc");
        }
        return ResponseEntity.ok("success");
    }
}
