package kr.kookmin.jeongo3.PostLike;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.kookmin.jeongo3.Post.Post;
import kr.kookmin.jeongo3.Post.PostRepository;
import kr.kookmin.jeongo3.User.User;
import kr.kookmin.jeongo3.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;


    public void savePostLike(String postId, String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        postLikeRepository.save(new PostLike(user, post));
    }

    public void deletePostLike(String postId, String userId) {
        PostLike postLike = postLikeRepository.findById(postId).orElseThrow();
        if (postLike.getUser().getId() != userId) {
            throw new RuntimeException(); // 커스텀 예외처리
        }
        postLikeRepository.delete(postLike);
    }


}
