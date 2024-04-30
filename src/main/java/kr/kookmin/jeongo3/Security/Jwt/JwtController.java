package kr.kookmin.jeongo3.Security.Jwt;

import kr.kookmin.jeongo3.Response;
import kr.kookmin.jeongo3.Security.Jwt.Dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("/refresh")
    public ResponseEntity<Response<TokenDto>> refresh(@RequestParam String accessToken,
                                            @RequestHeader("refreshToken") String refreshToken) {
        TokenDto tokenDto = jwtService.updateRefreshToken(accessToken, refreshToken);
        Response response = Response.builder().message("토큰 재발급 성공").data(tokenDto).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
