package kr.kookmin.jeongo3.PostLike;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.kookmin.jeongo3.Exception.MyException;
import kr.kookmin.jeongo3.Post.Post;
import kr.kookmin.jeongo3.Post.PostRepository;
import kr.kookmin.jeongo3.Response;
import kr.kookmin.jeongo3.User.User;
import kr.kookmin.jeongo3.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static kr.kookmin.jeongo3.Exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    public void savePostLike(String postId, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new MyException(USER_NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow(() -> new MyException(POST_NOT_FOUND));
        postLikeRepository.save(new PostLike(user, post));
    }

    public void deletePostLike(String postId, String userId) {
        PostLike postLike = postLikeRepository.findById(postId).orElseThrow(() -> new MyException(POSTLIKE_NOT_FOUND));
        if (postLike.getUser().getId() != userId) {
            throw new MyException(ACCESS_DENIED); // 커스텀 예외처리
        }
        postLikeRepository.delete(postLike);
    }
}
