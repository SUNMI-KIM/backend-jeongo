package kr.kookmin.jeongo3.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.kookmin.jeongo3.Security.JwtProvider;
import kr.kookmin.jeongo3.Security.TokenDto;
import kr.kookmin.jeongo3.User.Dto.RequestUserUpdateDto;
import kr.kookmin.jeongo3.User.Dto.RequestUserDto;
import kr.kookmin.jeongo3.User.Dto.ResponseUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final EntityManager entityManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public User findByLoginIdUser(String loginId) { // 없으면 사용자 없음 에러 추가
        return userRepository.findByLoginId(loginId).orElseThrow();
    }

    public ResponseUserDto findByIdUser(String id) { // 없으면 사용자 없음 에러 추가
        User user = userRepository.findById(id).orElseThrow();

        return ResponseUserDto.builder()
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
}

    public void saveUser(RequestUserDto requestUserDto) {// 유저가 이미 존재하는지 확인
        User user = requestUserDto.toEntity();
        user.setPassword(passwordEncoder.encode(requestUserDto.getPassword()));
        userRepository.save(user);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public TokenDto loginUser(String LoginId, String password) { // 커스텀 예외처리로 바꾸기
        User user = findByLoginIdUser(LoginId);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }
        String accessToken = jwtProvider.createAccessToken(user.getId(), user.getUserRole());
        String refreshToken = jwtProvider.createRefreshToken();
        return new TokenDto(accessToken, refreshToken);
    }

    @Transactional
    public int reportUser(String id) { // 유저가 존재하는지 확인
        return userRepository.updateReport(id);
    }

    @Transactional
    public void updateUser(String id, RequestUserUpdateDto requestUserUpdateDto) {
        User user = entityManager.find(User.class, id); // 없을때 예외 처리
        user.setName(requestUserUpdateDto.getName());
        user.setImage(requestUserUpdateDto.getImage());
        user.setUniv(user.getUniv());

        if (requestUserUpdateDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(requestUserUpdateDto.getPassword()));
        }
    }


}
