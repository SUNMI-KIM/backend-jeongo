package kr.kookmin.jeongo3.User;

import kr.kookmin.jeongo3.Security.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public String userRegister(@RequestBody User user) {
        userService.saveUser(user);
        return "저장되었습니다";
    }

    @GetMapping("/login")
    public TokenDto userLogin(@RequestParam String loginId, @RequestParam String password) {
        return userService.loginUser(loginId, password);
    }

    @GetMapping("/user")
    public User userDetails(Authentication authentication) {
        return userService.findByIdUser(authentication.getName());
    }

    @GetMapping("/users")
    public List<User> userList() {
        return userService.findAllUser();
    }

    @PatchMapping("/user/report")
    public int userReport(@RequestParam String id) {
        return userService.reportUser(id);
    }

}
