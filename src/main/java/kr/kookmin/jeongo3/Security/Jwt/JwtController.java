package kr.kookmin.jeongo3.Security.Jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import kr.kookmin.jeongo3.Exception.ErrorCode;
import kr.kookmin.jeongo3.Exception.MyException;
import kr.kookmin.jeongo3.Response;
import kr.kookmin.jeongo3.Security.Jwt.Dto.TokenDto;
import kr.kookmin.jeongo3.User.User;
import kr.kookmin.jeongo3.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository; // service 만들어라~^^ 어차피 대조도 해야하고.. Repository도 만들어야함

    @GetMapping("/refresh")
    public ResponseEntity<Response> refresh(@RequestParam String accessToken,
                                            @RequestHeader("RefreshToken") String refreshToken) {
        String userId;
        try {
            jwtProvider.validateToken(refreshToken);
            accessToken = jwtProvider.disassembleToken(accessToken);
            userId = jwtProvider.getUser(accessToken);
        } catch (SignatureException | MalformedJwtException | ArrayIndexOutOfBoundsException e) {
            throw new MyException(ErrorCode.WRONG_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new MyException(ErrorCode.EXPIRED_TOKEN);
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new MyException(ErrorCode.USER_NOT_FOUND));
        accessToken = jwtProvider.createAccessToken(user.getId(), user.getUserRole());
        refreshToken = jwtProvider.createRefreshToken(); // id refreshtoken 대조 적용
        TokenDto tokenDto = new TokenDto(accessToken, refreshToken);
        Response response = new Response("토큰 재발급", tokenDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
