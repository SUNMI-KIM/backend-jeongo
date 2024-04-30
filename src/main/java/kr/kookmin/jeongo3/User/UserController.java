package kr.kookmin.jeongo3.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kookmin.jeongo3.Exception.ExceptionDto;
import kr.kookmin.jeongo3.Response;
import kr.kookmin.jeongo3.Security.Jwt.Dto.TokenDto;
import kr.kookmin.jeongo3.User.Dto.RequestUserDto;
import kr.kookmin.jeongo3.User.Dto.RequestUserUpdateDto;
import kr.kookmin.jeongo3.User.Dto.ResponseUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "유저 서비스 회원 가입 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "유저 아이디 중복으로 인한 실패", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @PostMapping("/user")
    public ResponseEntity<Response<Object>> userRegister(@RequestBody RequestUserDto requestUserDto) {
        userService.saveUser(requestUserDto);
        Response response = Response.builder().message("회원가입 성공").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/login")
    public ResponseEntity<Response<TokenDto>> userLogin(@RequestParam String loginId, @RequestParam String password) {
        TokenDto tokenDto = userService.loginUser(loginId, password);
        Response response = Response.builder().message("로그인 성공").data(tokenDto).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/user")
    public ResponseEntity<Response<ResponseUserDto>> userDetails(Authentication authentication) {
        ResponseUserDto responseUserDto = userService.findByIdUser(authentication.getName());
        Response response = Response.builder().message("유저 상세 정보").data(responseUserDto).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/users")
    public List<User> userList() { // 얘는 디비 보는 용도
        return userService.findAllUser();
    }


    @PatchMapping("/user/report")
    public ResponseEntity<Response<Integer>> userReport(@RequestParam String id) {
        int report = userService.reportUser(id);
        Response response = Response.builder().message("유저 신고").data(report).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/user")
    public ResponseEntity<Response<Object>> userUpdate(Authentication authentication, @RequestBody RequestUserUpdateDto requestUserUpdateDto) {
        userService.updateUser(authentication.getName(), requestUserUpdateDto);
        Response response = Response.builder().message("유저 수정 성공").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
