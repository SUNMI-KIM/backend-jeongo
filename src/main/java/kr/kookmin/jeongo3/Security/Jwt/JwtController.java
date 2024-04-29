package kr.kookmin.jeongo3.Security.Jwt;

import kr.kookmin.jeongo3.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("/refresh")
    public ResponseEntity<Response> refresh(@RequestParam String accessToken,
                                            @RequestHeader("refreshToken") String refreshToken) {
        Response response = jwtService.updateRefreshToken(accessToken, refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
