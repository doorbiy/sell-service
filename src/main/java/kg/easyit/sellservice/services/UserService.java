package kg.easyit.sellservice.services;

import kg.easyit.sellservice.models.dtos.UserDto;
import kg.easyit.sellservice.models.entities.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> saveUser(UserDto userDto);

    ResponseEntity<?> sendCode(String login);

    ResponseEntity<?> getToken(String login, String code);

    ResponseEntity<?> verifyLogin(String token);

    boolean userLockOutChecking(User user);

    User findUserByLogin(String login);
}