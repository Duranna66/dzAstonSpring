package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import ru.alfabank.dto.UserDTO;
import ru.alfabank.entities.User;
import ru.alfabank.mappers.UserMapper;
import ru.alfabank.service.UserService;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@ComponentScan(basePackages = {"ru.alfabank.service"})
public class ServiceTest {
    @Mock
    UserMapper userMapper;
    @Mock
    List<User> users;
    @Mock
    UserService userService;
    private UserDTO createDTO(String login, String password, String checkPassword) {
        UserDTO userDTO = UserDTO.builder()
                .login(login)
                .password(password)
                .checkPassword(checkPassword)
                .build();
        return userDTO;
    }
    @Test
    public void saveTest_shouldBeAddedAtList() {
        UserDTO userDTO = createDTO("qwe", "test", "test");
        User expectedUser = User.builder()
                .login("qwe")
                .password("test").build();
        userService.save(userDTO);
        List<User> list = users;
        when(userService.getUsers().get(userService.getUsers().size() - 1)).thenReturn(expectedUser);
        Assertions.assertEquals(list.get(list.size() - 1).getLogin(), userDTO.getLogin());
    }

    @Test
    public void checkEqualsPasswords() {
        UserDTO userDTO = createDTO("qwe", "test", "test");
        when(userService.checkEqualsPasswords(userDTO)).thenReturn(userDTO.getPassword().equals(userDTO.getCheckPassword()));
        Assertions.assertTrue(userService.checkEqualsPasswords(userDTO));
    }
    @Test
    public void updatePassword() {
        UserDTO userDTO = createDTO("qwe", "test", "qwe");
        User user = userService.updatePassword(userDTO);
        Assertions.assertEquals(user.getPassword(), userDTO.getCheckPassword());
    }
    @Test
    public void checkExistUserOrNot() {
        UserDTO userDTO = createDTO("qwe", "test", null);
        userService.save(userDTO);
        Assertions.assertNotNull(userService.checkExistUserOrNot(userDTO));

    }
    @Test
    public void showAllUsersTest() {
        String login = "qwe";
        userService.save(createDTO(login, "test", null));
        List<User> list = userService.showAllUsers();
        Assertions.assertTrue(login.equals(list.get(list.size() - 1).getLogin()));
    }
}
