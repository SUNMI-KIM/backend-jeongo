package kr.kookmin.jeongo3.User;

import jakarta.transaction.Transactional;
import kr.kookmin.jeongo3.Security.JwtProvider;
import kr.kookmin.jeongo3.Security.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public User findByLoginIdUser(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow();
    }

    public User findByIdUser(String id) {
        return userRepository.findById(id).orElseThrow();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public TokenDto loginUser(String LoginId, String password) {
        User user = findByLoginIdUser(LoginId);
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException();
        }
        String accessToken = jwtProvider.createAccessToken(user.getId(), user.getUserRole());
        String refreshToken = jwtProvider.createRefreshToken();
        return new TokenDto(accessToken, refreshToken);
    }

    @Transactional
    public int reportUser(String id) {
        return userRepository.updateReport(id);
    }

    /*@Transactional
    public User updateUser(User user) {
        return user;
    }*/


}
