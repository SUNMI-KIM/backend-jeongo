package kr.kookmin.jeongo3.User;

import kr.kookmin.jeongo3.Security.TokenDto;
import kr.kookmin.jeongo3.User.Dto.RequestUserDto;
import kr.kookmin.jeongo3.User.Dto.RequestUserUpdateDto;
import kr.kookmin.jeongo3.User.Dto.ResponseUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public String userRegister(@RequestBody RequestUserDto requestUserDto) {
        userService.saveUser(requestUserDto);
        return "저장되었습니다";
    }

    @GetMapping("/login")
    public TokenDto userLogin(@RequestParam String loginId, @RequestParam String password) {
        return userService.loginUser(loginId, password);
    }

    @GetMapping("/user")
    public ResponseUserDto userDetails(Authentication authentication) {
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

    @PutMapping("/user")
    public String userUpdate(Authentication authentication, @RequestBody RequestUserUpdateDto requestUserUpdateDto) {
        userService.updateUser(authentication.getName(), requestUserUpdateDto);
        return "수정";
    }

}
