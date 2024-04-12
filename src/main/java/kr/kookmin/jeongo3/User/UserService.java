package kr.kookmin.jeongo3.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.kookmin.jeongo3.Exception.MyException;
import kr.kookmin.jeongo3.Response;
import kr.kookmin.jeongo3.Security.Jwt.JwtProvider;
import kr.kookmin.jeongo3.Security.Jwt.Dto.TokenDto;
import kr.kookmin.jeongo3.User.Dto.RequestUserUpdateDto;
import kr.kookmin.jeongo3.User.Dto.RequestUserDto;
import kr.kookmin.jeongo3.User.Dto.ResponseUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.kookmin.jeongo3.Exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final EntityManager entityManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public Response findByIdUser(String id) { // 없으면 사용자 없음 에러 추가
        User user = userRepository.findById(id).orElseThrow(() -> new MyException(USER_NOT_FOUND));
        ResponseUserDto responseUserDto = ResponseUserDto.builder()
                .userRole(user.getUserRole())
                .name(user.getName())
                .email(user.getEmail())
                .gender(user.getGender())
                .age(user.getAge())
                .loginId(user.getLoginId())
                .phoneNum(user.getPhoneNum())
                .univ(user.getUniv())
                .department(user.getDepartment())
                .image(user.getImage())
                .point(user.getPoint())
                .build();
        return new Response("유저 조회", responseUserDto);
}

    public Response saveUser(RequestUserDto requestUserDto) {
        if (!userRepository.existsByLoginId(requestUserDto.getLoginId())) {
            throw new MyException(DUPLICATED_USER_ID);
        }
        User user = requestUserDto.toEntity();
        user.setPassword(passwordEncoder.encode(requestUserDto.getPassword()));
        userRepository.save(user);
        return new Response("유저 저장", HttpStatus.OK);
    }

    public List<User> findAllUser() { // 디버깅용
        return userRepository.findAll();
    }

    public Response loginUser(String loginId, String password) { // 커스텀 예외처리로 바꾸기
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new MyException(USER_NOT_FOUND));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new MyException(WRONG_PASSWORD);
        }
        String accessToken = jwtProvider.createAccessToken(user.getId(), user.getUserRole());
        String refreshToken = jwtProvider.createRefreshToken();
        TokenDto tokenDto = new TokenDto(accessToken, refreshToken);
        return new Response("로그인", tokenDto);
    }

    @Transactional
    public Response reportUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new MyException(USER_NOT_FOUND);
        }
        return new Response("유저 신고", userRepository.updateReport(id));
    }

    @Transactional
    public Response updateUser(String id, RequestUserUpdateDto requestUserUpdateDto) {
        if (!userRepository.existsById(id)) {
            throw new MyException(USER_NOT_FOUND);
        }
        User user = entityManager.find(User.class, id);
        user.setName(requestUserUpdateDto.getName());
        user.setImage(requestUserUpdateDto.getImage());
        user.setUniv(user.getUniv());

        if (requestUserUpdateDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(requestUserUpdateDto.getPassword()));
        }

        return new Response("유저 수정", HttpStatus.OK);
    }


}
