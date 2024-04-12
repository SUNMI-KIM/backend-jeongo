package kr.kookmin.jeongo3.User;

import kr.kookmin.jeongo3.Response;
import kr.kookmin.jeongo3.User.Dto.RequestUserDto;
import kr.kookmin.jeongo3.User.Dto.RequestUserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Response> userRegister(@RequestBody RequestUserDto requestUserDto) {
        Response response = userService.saveUser(requestUserDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/login")
    public ResponseEntity<Response> userLogin(@RequestParam String loginId, @RequestParam String password) {
        Response response = userService.loginUser(loginId, password);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/user")
    public ResponseEntity<Response> userDetails(Authentication authentication) {
        Response response = userService.findByIdUser(authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/users")
    public List<User> userList() { // 얘는 디비 보는 용도
        return userService.findAllUser();
    }

    @PatchMapping("/user/report")
    public ResponseEntity<Response> userReport(@RequestParam String id) {
        Response response = userService.reportUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/user")
    public ResponseEntity<Response> userUpdate(Authentication authentication, @RequestBody RequestUserUpdateDto requestUserUpdateDto) {
        Response response = userService.updateUser(authentication.getName(), requestUserUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
