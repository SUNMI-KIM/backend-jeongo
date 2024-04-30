package kr.kookmin.jeongo3.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.kookmin.jeongo3.Exception.ErrorCode;
import kr.kookmin.jeongo3.Exception.MyException;
import kr.kookmin.jeongo3.Response;
import kr.kookmin.jeongo3.Security.Jwt.Jwt;
import kr.kookmin.jeongo3.Security.Jwt.JwtProvider;
import kr.kookmin.jeongo3.Security.Jwt.Dto.TokenDto;
import kr.kookmin.jeongo3.Security.Jwt.JwtRepository;
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
    private final JwtRepository jwtRepository;

    public ResponseUserDto findByIdUser(String id) { // 없으면 사용자 없음 에러 추가
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

        return responseUserDto;
}

    public void saveUser(RequestUserDto requestUserDto) {
        if (userRepository.existsByLoginId(requestUserDto.getLoginId())) {
            throw new MyException(DUPLICATED_USER_ID);
        }
        User user = requestUserDto.toEntity();
        user.setPassword(passwordEncoder.encode(requestUserDto.getPassword()));
        user.setUserRole(UserRole.UNIV);
        userRepository.save(user);
    }

    public List<User> findAllUser() { // 디버깅용
        return userRepository.findAll();
    }

    @Transactional
    public TokenDto loginUser(String loginId, String password) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new MyException(USER_NOT_FOUND));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new MyException(WRONG_PASSWORD);
        }
        String accessToken = jwtProvider.createAccessToken(user.getId(), user.getUserRole());
        String refreshToken;

        if (jwtRepository.existsByUser(user)) {
            Jwt jwt = jwtRepository.findByUser(user).orElseThrow(() -> new MyException(LOGIN_NOT_FOUND));
            refreshToken = jwtProvider.createRefreshToken();
            jwt.setRefreshToken(refreshToken);
        }
        else {
            refreshToken = jwtProvider.createRefreshToken();
            jwtRepository.save(new Jwt(user, refreshToken));
        }
        return new TokenDto(accessToken, refreshToken);
    }

    @Transactional
    public int reportUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new MyException(USER_NOT_FOUND);
        }
        return userRepository.updateReport(id);
    }

    @Transactional
    public void updateUser(String id, RequestUserUpdateDto requestUserUpdateDto) {
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
    }
}
