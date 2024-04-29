package kr.kookmin.jeongo3.DISC;

import kr.kookmin.jeongo3.Exception.ErrorCode;
import kr.kookmin.jeongo3.Exception.MyException;
import kr.kookmin.jeongo3.Response;
import kr.kookmin.jeongo3.User.User;
import kr.kookmin.jeongo3.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DISCService {

    private final UserRepository userRepository;
    private final DISCRepository discRepository;

    /*public Response findDISC(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new MyException(ErrorCode.USER_NOT_FOUND));
        DISC disc = discRepository.findByUser(user).orElseThrow(() -> new MyException(ErrorCode.DISC_NOT_FOUND));


    }*/

}
